package com.cookainno.mobile.ui.screens.ingredients

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IngredientsViewModel : ViewModel() {
    // A private mutable state flow to hold the list of ingredients
    private val _ingredients = MutableStateFlow<List<String>>(emptyList())

    // A public state flow to expose as an immutable list of ingredients
    val ingredients = _ingredients.asStateFlow()

    // Function to add a new ingredient to the list
    fun addIngredient(ingredient: String) {
        _ingredients.value = _ingredients.value + ingredient
    }

    // Function to update an ingredient at a specific index
    fun updateIngredient(index: Int, newIngredient: String) {
        if (index in _ingredients.value.indices) {
            _ingredients.value = _ingredients.value.toMutableList().apply {
                set(index, newIngredient)
            }
        }
    }

    // Function to remove an ingredient from the list
    fun removeIngredient(index: Int) {
        if (index in _ingredients.value.indices) {
            _ingredients.value = _ingredients.value.toMutableList().apply {
                removeAt(index)
            }
        }
    }
}
