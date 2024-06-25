package com.cookainno.mobile.ui.screens.favourites

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.recipes.RecipeItem
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel
import com.cookainno.mobile.ui.screens.recipes.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouritesScreen(recipesViewModel: RecipesViewModel, navController: NavHostController) {
    val favouriteRecipes by recipesViewModel.favouriteRecipes.collectAsState()
    val searchQuery = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        recipesViewModel.getFavouriteRecipes()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            shape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25),
            searchQuery.value,
            navController
        ) {
            searchQuery.value = it
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)
        ) {
            items(favouriteRecipes ?: emptyList()) { recipe ->
                RecipeItem(recipe = recipe, onClick = {
                    navController.navigate(NavRoutes.DETAILS.name)
                })
            }
        }
    }
}
