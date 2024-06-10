package com.cookainno.mobile.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cookainno.mobile.ui.screens.auth.AuthViewModel
import com.cookainno.mobile.ui.screens.camera.CamViewModel
import com.cookainno.mobile.ui.screens.home.HomeScreen
import com.cookainno.mobile.ui.screens.home.RecipesViewModel
import com.cookainno.mobile.ui.screens.profile.ProfileViewModel

@Composable
fun App(
    authViewModel: AuthViewModel,
    camViewModel: CamViewModel,
    recipesViewModel: RecipesViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.HOME.name) {
        composable(NavRoutes.HOME.name) {
            HomeScreen()
        }
    }
}
