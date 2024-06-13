package com.cookainno.mobile.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.ui.screens.LoadingScreen
import com.cookainno.mobile.ui.screens.auth.AuthScreen
import com.cookainno.mobile.ui.screens.auth.AuthViewModel
import com.cookainno.mobile.ui.screens.camera.CamViewModel
import com.cookainno.mobile.ui.screens.home.HomeScreen
import com.cookainno.mobile.ui.screens.profile.ProfileViewModel
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(
    authViewModel: AuthViewModel,
    camViewModel: CamViewModel,
    recipesViewModel: RecipesViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    val isSignedIn by authViewModel.isSignedIn.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    if (isLoading) {
        Scaffold {
            LoadingScreen()
        }
    } else {
        val startScreen = if (isSignedIn) NavRoutes.HOME.name else NavRoutes.AUTH.name
        NavHost(navController = navController, startDestination = startScreen) {
            composable(NavRoutes.HOME.name) {
                HomeScreen(
                    camViewModel = camViewModel,
                    recipesViewModel = recipesViewModel,
                    profileViewModel = profileViewModel
                )
            }
            composable(NavRoutes.AUTH.name) {
                AuthScreen()
            }
        }
    }
}
