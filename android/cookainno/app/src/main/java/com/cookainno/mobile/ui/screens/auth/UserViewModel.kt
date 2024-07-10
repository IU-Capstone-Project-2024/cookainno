package com.cookainno.mobile.ui.screens.auth

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.model.UserDataResponse
import com.cookainno.mobile.data.repository.AuthRepository
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.data.repository.RecomendationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class UserViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _navigateToConfirmation = MutableStateFlow(false)
    val navigateToConfirmation: StateFlow<Boolean> = _navigateToConfirmation

    private val _navigateToInit = MutableStateFlow(false)
    val navigateToInit: StateFlow<Boolean> = _navigateToInit

    private val _navigateToMain = MutableStateFlow(false)
    val navigateToMain: StateFlow<Boolean> = _navigateToMain

    private val _registrationError = MutableStateFlow<String?>(null)
    val registrationError: StateFlow<String?> = _registrationError

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmationCode = MutableStateFlow("")
    val confirmationCode: StateFlow<String> = _confirmationCode

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val _advice = MutableStateFlow("You are beautiful as you are!")
    val advice: StateFlow<String> = _advice

    private val _userData = MutableStateFlow<UserDataResponse?>(null)
    val userData: StateFlow<UserDataResponse?> = _userData

    private var authRepository: AuthRepository
    private var recomendationRepository: RecomendationRepository

    private val _userId = MutableStateFlow(-1)
    val userId: StateFlow<Int> = _userId

    init {
        viewModelScope.launch {
            _isSignedIn.value = checkLoggedIn()
            _isLoading.value = false
        }
        authRepository = AuthRepository(preferencesRepository)
        recomendationRepository = RecomendationRepository(preferencesRepository)
    }

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onConfirmationChange(newConfirmation: String) {
        _confirmationCode.value = newConfirmation
    }

    fun onHeightChange(newHeight: String) {
        _height.value = newHeight
    }

    fun onWeightChange(newWeight: String) {
        _weight.value = newWeight
    }

    fun onDateChange(newDate: String) {
        _date.value = newDate
    }

    private suspend fun checkLoggedIn(): Boolean {
        val token = preferencesRepository.getTokenFlow().first()
        return token != null
    }

    fun signUp() {
        _registrationError.value = null
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.register(
                username = _username.value,
                email = _email.value,
                password = _password.value
            )
            if (result.isSuccess) {
                Log.d("PPP", "signUp: ${result.isSuccess}")
                _navigateToConfirmation.value = true
            } else {
                _registrationError.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun confirmCode() {
        _registrationError.value = null
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.confirm(
                email = _email.value,
                confirmationCode = _confirmationCode.value
            )
            if (result.isSuccess) {
                val loginResult =
                    authRepository.login(username = _username.value, password = _password.value)
                if (loginResult.isSuccess) {
                    _navigateToInit.value = true
                } else {
                    _registrationError.value = loginResult.exceptionOrNull()?.message
                }
                _isLoading.value = false
            } else {
                _registrationError.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun signIn() {
        _registrationError.value = null
        _navigateToMain.value = false
        viewModelScope.launch {
            _isLoading.value = true
            val result =
                authRepository.login(username = _username.value, password = _password.value)
            _userId.value = authRepository.getUserID()
            if (result.isSuccess) {
                _navigateToMain.value = true
            } else {
                _registrationError.value = result.exceptionOrNull()?.message
            }
            initUserId()
            _isLoading.value = false
        }
    }

    fun updateUserData(height: Int, weight: Int, date: String) {
        recomendationRepository.initToken()
        viewModelScope.launch {
            val resp = recomendationRepository.putUserData(_userId.value, height, weight, date)
            Log.d("UMPALUMPA", "updateUserData: ${resp.isSuccess} ${_userId.value}")
            if (resp.isSuccess) {
                _navigateToMain.value = true
            }
        }
    }

    fun calculateCalories(weight: Int, height: Int, age: Int): Int {
        val bmr: Double = 10 * weight + 6.25 * height - 5 * age - 100
        val calories = bmr * 1.2
        return calories.toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateAge(dateOfBirth: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dob = LocalDate.parse(dateOfBirth, formatter)
        val currentDate = LocalDate.now()
        val age = Period.between(dob, currentDate).years
        return age
    }

    fun getUserData() {
        recomendationRepository.initToken()
        viewModelScope.launch {
            val resp = recomendationRepository.getUserData(_userId.value)
            if (resp.isSuccess) {
                _userData.value = resp.getOrNull()
                _weight.value = resp.getOrNull()?.weight.toString()
                _height.value = resp.getOrNull()?.height.toString()
                _date.value = resp.getOrNull()?.dateOfBirth.toString()
            } else {
                _userData.value =
                    UserDataResponse(-1, "User", "example@example.com", 0, 0, "1970-01-01")
            }
        }
    }

    fun generateAdvice() {
        viewModelScope.launch {
            val resp = recomendationRepository.generateAdvice("normal consumption")
            if (resp.isSuccess && resp.getOrNull() != null) {
                _advice.value = resp.getOrNull()!!
            } else {
                _advice.value = "You are beautiful as you are!"
            }
        }
    }

    fun resetToConfirmation() {
        _navigateToConfirmation.value = false
    }

    fun resetToInit() {
        _navigateToInit.value = false
    }

    fun resetToMain() {
        _navigateToMain.value = false
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun initUserId() {
        if (_userId.value == -1) {
            viewModelScope.launch {
                _userId.value = authRepository.getUserID()
            }
        }
    }
}
