package com.cookainno.mobile.ui.screens.recipes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.data.repository.RecipesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesViewModel(preferencesRepository: PreferencesRepository) : ViewModel() {
    private val recipesRepository = RecipesRepository(preferencesRepository)
    private val _okExample = MutableStateFlow(false)
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe

    private val _recipes = MutableStateFlow<List<Recipe>?>(null)
    val recipes: StateFlow<List<Recipe>?> = _recipes

    private val _selectedRecipe = MutableStateFlow<Recipe?>(null)
    val selectedRecipe: StateFlow<Recipe?> = _selectedRecipe

    private val _favouriteRecipes = MutableStateFlow<List<Recipe>?>(null)
    val favouriteRecipes: StateFlow<List<Recipe>?> = _favouriteRecipes

    private val _isFavouriteRefreshing = MutableStateFlow(false)
    val isFavouriteRefreshing: StateFlow<Boolean> = _isFavouriteRefreshing

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var allFavourites: MutableList<Recipe>? = mutableListOf()

    private var userId = -1

    private var currentFavouritesPage = 0

    private var currentPage = 0
    private val pageSize = 10

    private var hasMoreRecipes = true
    private var hasMoreFavouriteRecipes = true

    fun initRepository() {
        recipesRepository.initToken()
    }

    fun initUserId(id: Int) {
        userId = id
        getAllFavouriteRecipes()
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

    fun getRecipesSortedByLikes() {
        if (!hasMoreRecipes) return
        _isRefreshing.value = true
        viewModelScope.launch {
            if (currentPage == 0) delay(250)
            val newRecipes =
                recipesRepository.getRecipesSortedByLikes(currentPage, pageSize).getOrNull()
            if (!newRecipes.isNullOrEmpty()) {
                _recipes.value = _recipes.value.orEmpty() + newRecipes
                if (newRecipes.size < pageSize) {
                    hasMoreRecipes = false
                } else {
                    currentPage++
                }
            }
            _isRefreshing.value = false
        }
    }

    fun searchRecipes(name: String) {
        if (!hasMoreRecipes) return
        _isRefreshing.value = true
        viewModelScope.launch {
            val newRecipes =
                recipesRepository.searchRecipes(name, currentPage, pageSize).getOrNull()
            if (!newRecipes.isNullOrEmpty()) {
                _recipes.value = _recipes.value.orEmpty() + newRecipes
                if (newRecipes.size < pageSize) {
                    hasMoreRecipes = false
                } else {
                    currentPage++
                }
            }
            _isRefreshing.value = false
        }
    }

    fun isFavourite(recipe: Recipe): Boolean {
        Log.d("FAVCHECK", "isFavourite: ${recipe.name} ${allFavourites?.contains(recipe)?:false}")
        Log.d("ALLFAVS", "isFavourite: ${allFavourites?.map { it.name }}")
        return allFavourites?.map { it.name }?.contains(recipe.name)?:false
    }

    fun selectRecipe(recipe: Recipe) {
        Log.d("RECHECK", "selectRecipe: $recipe")
        _selectedRecipe.value = recipe
    }

    fun addFavouriteRecipe(recipe: Recipe) {
        allFavourites?.add(recipe)
        viewModelScope.launch {
            recipesRepository.addFavouriteRecipe(userId, recipe.id)
        }
    }

    fun deleteFavouriteRecipe(recipe: Recipe) {
        allFavourites?.remove(recipe)
        viewModelScope.launch {
            recipesRepository.deleteFavouriteRecipe(userId, recipe.id)
        }
    }

    fun getFavouriteRecipes() {
        if (!hasMoreFavouriteRecipes) return
        _isFavouriteRefreshing.value = true
        viewModelScope.launch {
            val newRecipes = recipesRepository.getFavouriteRecipes(
                userId,
                currentFavouritesPage,
                pageSize,
                false
            ).getOrNull()
            if (!newRecipes.isNullOrEmpty()) {
                Log.d("TTT", "getFavouriteRecipes: ${newRecipes.map { it.name }}")
                _favouriteRecipes.value = _favouriteRecipes.value.orEmpty() + newRecipes
                if (newRecipes.size < pageSize) {
                    hasMoreFavouriteRecipes = false
                } else {
                    currentFavouritesPage++
                }
            }
            _isFavouriteRefreshing.value = false
        }
    }

    fun searchFavouriteRecipes(name: String) {
        if (!hasMoreFavouriteRecipes) return
        _isFavouriteRefreshing.value = true
        viewModelScope.launch {
            val newRecipes = recipesRepository.searchFavouriteRecipes(
                userId,
                name,
                currentFavouritesPage,
                pageSize
            ).getOrNull()
            if (!newRecipes.isNullOrEmpty()) {
                _favouriteRecipes.value = _favouriteRecipes.value.orEmpty() + newRecipes
                if (newRecipes.size < pageSize) {
                    hasMoreFavouriteRecipes = false
                } else {
                    currentFavouritesPage++
                }
            }
            _isFavouriteRefreshing.value = false
        }
    }

    fun resetRefreshings() {
        _isRefreshing.value = false
        _isFavouriteRefreshing.value = false
    }

    fun getAllFavouriteRecipes() {
        viewModelScope.launch {
            allFavourites =
                recipesRepository.getFavouriteRecipes(userId, 0, 10000, false).getOrNull()?.toMutableList()
        }
    }

    fun resetFavouritePagination() {
        currentFavouritesPage = 0
        hasMoreFavouriteRecipes = true
        _favouriteRecipes.value = listOf()
    }

    fun resetPagination() {
        currentPage = 0
        hasMoreRecipes = true
        _recipes.value = listOf()
    }
}
