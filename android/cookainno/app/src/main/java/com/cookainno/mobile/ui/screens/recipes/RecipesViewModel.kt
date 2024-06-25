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

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    private var currentPage = 0
    private val pageSize = 10

    fun initRepository() {
        recipesRepository.initToken()
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

    fun getRecipesSortedByLikes() {
        viewModelScope.launch {
            val newRecipes = recipesRepository.getRecipesSortedByLikes(currentPage, pageSize).getOrNull()
            if (!newRecipes.isNullOrEmpty()) {
                _recipes.value = _recipes.value.orEmpty() + newRecipes
                currentPage++
            }
        }
    }

    fun selectRecipe(recipe: Recipe) {
        _selectedRecipe.value = recipe
    }
}