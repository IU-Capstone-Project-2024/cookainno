package com.cookainno.mobile.ui.screens.generation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun GeneratedRecipe(ingredientsViewModel: IngredientsViewModel, navController: NavHostController) {
    val recipe by ingredientsViewModel.recipes.collectAsState()
    if (recipe.isNotEmpty()) {
        Column {
            Text(text = recipe.first().name)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "${recipe.first().ingredients}")
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = recipe.first().instruction)
        }
    }
}
