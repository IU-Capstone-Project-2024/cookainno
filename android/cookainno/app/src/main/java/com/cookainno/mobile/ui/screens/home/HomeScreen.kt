package com.cookainno.mobile.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.ui.NavRoutes
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.camera.CamViewModel
import com.cookainno.mobile.ui.screens.camera.CameraScreen
import com.cookainno.mobile.ui.screens.profile.ProfileScreen
import com.cookainno.mobile.ui.screens.recipes.RecipesScreen
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    authViewModel: UserViewModel,
    camViewModel: CamViewModel,
    recipesViewModel: RecipesViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
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
                        contentDescription = "Favourite icon"
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
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home icon")
                }
                IconButton(
                    onClick = {
                        navController.navigate(NavRoutes.PROFILE.name)
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Profile icon")
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = NavRoutes.RECIPES.name) {
            composable(NavRoutes.RECIPES.name) {
                recipesViewModel.initRepository()
                // move cam functionality here
                RecipesScreen(camViewModel = camViewModel, recipesViewModel = recipesViewModel)
            }
            composable(NavRoutes.CAMERA.name) {
                CameraScreen(camViewModel = camViewModel)
            }
            composable(NavRoutes.PROFILE.name) {
                ProfileScreen(authViewModel = authViewModel)
            }
        }
    }
}