package com.cookainno.mobile.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(private val preferencesRepository: PreferencesRepository) : ViewModel() {
    private val _isSignedIn = MutableStateFlow(false)
    val isSignedIn: StateFlow<Boolean> = _isSignedIn

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isSignedIn.value = checkLoggedIn()
            _isLoading.value = false
        }
    }

    private suspend fun checkLoggedIn(): Boolean {
        return getUserToken() == null
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            preferencesRepository.saveData("user_token", token)
            _isSignedIn.value = true
        }
    }

    private suspend fun getUserToken(): String? {
        return preferencesRepository.getData("user_token").first()
    }
}
