package com.cookainno.mobile.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.data.repository.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesViewModel(preferencesRepository: PreferencesRepository): ViewModel() {
    private val recipesRepository = RecipesRepository(preferencesRepository)
    private val _okExample = MutableStateFlow(false)
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    private val _recipes = MutableStateFlow<List<Recipe>?>(null)
    val recipes: StateFlow<List<Recipe>?> = _recipes

    fun initRepository() {
        recipesRepository.initToken()
        getRecipes()
    }

    fun example() {
        viewModelScope.launch {
            _okExample.value = recipesRepository.example()
        }
    }

    fun getRecipe(id: Int) {
        viewModelScope.launch {
            _recipe.value = recipesRepository.getRecipe(id).getOrNull()
        }
    }

    private fun getRecipes() {
        getTempRecipes()
        viewModelScope.launch {
            _recipes.value = recipesRepository.getRecipes().getOrNull()
        }
    }

    private fun getTempRecipes() {
        val l = mutableListOf<Recipe>()
        viewModelScope.launch {
            for (i in 1..10) {
                recipesRepository.getRecipe(i).getOrNull()?.let { l.add(it) }
            }
            _recipes.value = l
        }
    }
}