package com.cookainno.mobile.ui.screens.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel

@Composable
fun RecipeDetailsScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val recipe by recipesViewModel.selectedRecipe.collectAsState()
    Text(text = "$recipe")
}
