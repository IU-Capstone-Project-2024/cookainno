package com.cookainno.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cookainno.mobile.ui.App
import com.cookainno.mobile.ui.screens.auth.AuthViewModel
import com.cookainno.mobile.ui.screens.camera.CamViewModel
import com.cookainno.mobile.ui.screens.home.RecipesViewModel
import com.cookainno.mobile.ui.screens.profile.ProfileViewModel
import com.cookainno.mobile.ui.theme.CookainnoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookainnoTheme {
                val authViewModel = AuthViewModel()
                val camViewModel = CamViewModel()
                val recipesViewModel = RecipesViewModel()
                val profileViewModel = ProfileViewModel()
                //
                App(authViewModel, camViewModel, recipesViewModel, profileViewModel)
            }
        }
    }
}
