package com.cookainno.mobile.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.details.RecipeDetailsScreen
import com.cookainno.mobile.ui.screens.generation.CamViewModel
import com.cookainno.mobile.ui.screens.favourites.FavouritesScreen
import com.cookainno.mobile.ui.screens.generation.GeneratedRecipe
import com.cookainno.mobile.ui.screens.generation.IngredientsScreen
import com.cookainno.mobile.ui.screens.generation.IngredientsViewModel
import com.cookainno.mobile.ui.screens.profile.ProfileScreen
import com.cookainno.mobile.ui.screens.recipes.RecipesScreen
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    userViewModel: UserViewModel,
    camViewModel: CamViewModel,
    recipesViewModel: RecipesViewModel,
    ingredientsViewModel: IngredientsViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(

        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .padding(horizontal = 80.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onBackground),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(NavRoutes.CAMERA.name)
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favourite icon",
                            tint = if (currentRoute == NavRoutes.CAMERA.name) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.graphicsLayer(alpha = 1f)
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(NavRoutes.RECIPES.name)
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home icon",
                            tint = if (currentRoute == NavRoutes.RECIPES.name) Color.White else (MaterialTheme.colorScheme.onPrimaryContainer),
                            modifier = Modifier.graphicsLayer(alpha = 1f)
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(NavRoutes.PROFILE.name)
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile icon",
                            tint = if (currentRoute == NavRoutes.PROFILE.name) Color.White else MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = NavRoutes.RECIPES.name) {
                composable(NavRoutes.RECIPES.name) {
                    recipesViewModel.initRepository()
                    ingredientsViewModel.emptyRecipes()
                    ingredientsViewModel.emptyIngredients()
                    userViewModel.initUserId()
                    RecipesScreen(
                        recipesViewModel = recipesViewModel,
                        navController = navController
                    )
                }
                composable(NavRoutes.CAMERA.name) {
                    FavouritesScreen()
                }
                composable(NavRoutes.PROFILE.name) {
                    ProfileScreen(authViewModel = userViewModel)
                }
                composable(NavRoutes.INGREDIENTS.name) {
                    IngredientsScreen(
                        camViewModel = camViewModel,
                        ingredientsViewModel = ingredientsViewModel,
                        navController = navController
                    )
                }
                composable(NavRoutes.GENERATED.name) {
                    GeneratedRecipe(ingredientsViewModel = ingredientsViewModel, navController = navController)
                }
                composable("${NavRoutes.DETAILS.name}/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (id != null) {
                        val recipe = recipesViewModel.recipes.collectAsState().value?.get(id-1)
                        if (recipe != null) {
                            RecipeDetailsScreen(recipe = recipe, navController = navController)
                        }
                    } else {
                        // handle id is null
                    }
                }
            }
        }
    }
}
