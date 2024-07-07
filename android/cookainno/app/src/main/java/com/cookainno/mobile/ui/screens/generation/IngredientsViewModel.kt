package com.cookainno.mobile.ui.screens.generation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.model.GeneratedRecipe
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.data.model.RecipeToAdd
import com.cookainno.mobile.data.repository.GenerationRepository
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.data.repository.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class IngredientsViewModel(preferencesRepository: PreferencesRepository) : ViewModel() {
    private val _ingredients = MutableStateFlow<List<String>>(emptyList())

    val ingredients = _ingredients.asStateFlow()

    private val generationRepository = GenerationRepository()
    private val recipesRepository = RecipesRepository(preferencesRepository)

    private val _recipes = MutableStateFlow<List<GeneratedRecipe>>(emptyList())
    val recipes = _recipes.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun initRepository() {
        recipesRepository.initToken()
    }

    fun addIngredient(ingredient: String) {
        _ingredients.value += ingredient
    }

    fun updateIngredient(index: Int, newIngredient: String) {
        if (index in _ingredients.value.indices) {
            _ingredients.value = _ingredients.value.toMutableList().apply {
                set(index, newIngredient)
            }
        }
    }

    fun removeIngredient(index: Int) {
        if (index in _ingredients.value.indices) {
            _ingredients.value = _ingredients.value.toMutableList().apply {
                removeAt(index)
            }
        }
    }

    fun generateRecipes() {
        _isLoading.value = true
        viewModelScope.launch {
            val response =
                generationRepository.generateRecipes(_ingredients.value.filter { it != "" })
            if (response.isSuccess) {
                _recipes.value = response.getOrNull()!!.recipes
                addRecipeToRemote()
                _isLoading.value = false
            } else {
                _isLoading.value = false
            }
        }
    }

    fun detectIngredients(file: File) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = generationRepository.detectIngredients(file)
            if (response.isSuccess && !response.getOrNull().isNullOrEmpty()) {
                _ingredients.value += response.getOrNull()!!
            }
            _isLoading.value = false
        }
    }

    private fun addRecipeToRemote() {
        viewModelScope.launch {
            if (_recipes.value.isNotEmpty()) {
                recipesRepository.addGeneratedRecipe(convertToRecipeToAdd(_recipes.value[0]))
            }
        }
    }

    private fun convertToRecipeToAdd(recipe: GeneratedRecipe): RecipeToAdd {
        val oldIngredients = recipe.ingredients.toString()
        val ingredients = oldIngredients.substring(1, oldIngredients.length-2)
        return RecipeToAdd(recipe.name, ingredients, recipe.instructions, recipe.imageUrl)
    }

    fun emptyIngredients() {
        _ingredients.value = emptyList()
    }

    fun emptyRecipes() {
        _recipes.value = emptyList()
    }
}
