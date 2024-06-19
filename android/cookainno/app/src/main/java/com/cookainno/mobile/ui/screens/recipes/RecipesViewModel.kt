package com.cookainno.mobile.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.data.repository.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesViewModel(preferencesRepository: PreferencesRepository): ViewModel() {
    private val recipesRepository = RecipesRepository(preferencesRepository)
    private val _okExample = MutableStateFlow(false)
    val okExample: StateFlow<Boolean> = _okExample

    fun initRepository() {
        recipesRepository.initToken()
    }

    fun example() {
        viewModelScope.launch {
            _okExample.value = recipesRepository.example()
        }
    }
}