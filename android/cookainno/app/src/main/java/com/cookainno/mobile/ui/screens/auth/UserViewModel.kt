package com.cookainno.mobile.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.repository.AuthRepository
import com.cookainno.mobile.data.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _navigateToConfirmation = MutableStateFlow(false)
    val navigateToConfirmation: StateFlow<Boolean> = _navigateToConfirmation

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

    private var authRepository: AuthRepository

    init {
        viewModelScope.launch {
            _isSignedIn.value = checkLoggedIn()
            _isLoading.value = false
        }
        authRepository = AuthRepository(preferencesRepository)
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

    private suspend fun checkLoggedIn(): Boolean {
        val token = preferencesRepository.getTokenFlow().first()
        return token != null
    }

    fun signUp() {
        _registrationError.value = null
        _navigateToConfirmation.value = false
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.register(
                username = _username.value,
                email = _email.value,
                password = _password.value
            )
            if (result.isSuccess) {
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
                    _navigateToMain.value = true
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
            if (result.isSuccess) {
                _navigateToMain.value = true
            } else {
                _registrationError.value = result.exceptionOrNull()?.message
            }
            _isLoading.value = false
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
