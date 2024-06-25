package com.cookainno.mobile.ui.screens.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.cookainno.mobile.data.model.Recipe

@Composable
fun RecipeDetailsScreen(recipe: Recipe, navController: NavHostController) {
    Text(text = recipe.name)
}
