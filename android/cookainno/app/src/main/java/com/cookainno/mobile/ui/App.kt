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
import com.cookainno.mobile.ui.screens.auth.RegistrationScreen
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.auth.ConfirmationCodeScreen
import com.cookainno.mobile.ui.screens.auth.InitializingUserScreen
import com.cookainno.mobile.ui.screens.auth.LoginScreen
import com.cookainno.mobile.ui.screens.generation.CamViewModel
import com.cookainno.mobile.ui.screens.home.HomeScreen
import com.cookainno.mobile.ui.screens.generation.IngredientsViewModel
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(
    userViewModel: UserViewModel,
    camViewModel: CamViewModel,
    recipesViewModel: RecipesViewModel,
    ingredientsViewModel: IngredientsViewModel
) {
    val navController = rememberNavController()
    val isSignedIn by userViewModel.isSignedIn.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    if (isLoading) {
        Scaffold {
            LoadingScreen()
        }
    } else {
        val startScreen = if (isSignedIn) NavRoutes.HOME.name else NavRoutes.LOGIN.name
        NavHost(navController = navController, startDestination = startScreen) {
            composable(NavRoutes.HOME.name) {
                userViewModel.generateAdvice()
                HomeScreen(
                    userViewModel = userViewModel,
                    camViewModel = camViewModel,
                    recipesViewModel = recipesViewModel,
                    ingredientsViewModel = ingredientsViewModel
                )
            }
            composable(NavRoutes.REGISTRATION.name) {
                RegistrationScreen(userViewModel = userViewModel, navController = navController)
            }
            composable(NavRoutes.CONFIRMATION.name) {
                ConfirmationCodeScreen(userViewModel = userViewModel, navController = navController)
            }
            composable(NavRoutes.USERINIT.name) {
                InitializingUserScreen(userViewModel = userViewModel, navController = navController)
            }
            composable(NavRoutes.LOGIN.name) {
                LoginScreen(userViewModel = userViewModel, navController = navController)
            }
        }
    }
}
