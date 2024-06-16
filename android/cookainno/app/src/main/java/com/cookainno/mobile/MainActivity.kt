package com.cookainno.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.ui.App
import com.cookainno.mobile.ui.screens.auth.AuthViewModel
import com.cookainno.mobile.ui.screens.camera.CamViewModel
import com.cookainno.mobile.ui.screens.profile.ProfileViewModel
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel
import com.cookainno.mobile.ui.theme.CookainnoTheme
import com.cookainno.mobile.utilities.ImageUtility

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cameraUtility = ImageUtility(this)
        setContent {
            CookainnoTheme {
                val authViewModel = AuthViewModel(PreferencesRepository(this))
                val camViewModel = CamViewModel(imageUtility = cameraUtility)
                val recipesViewModel: RecipesViewModel by viewModels()
                val profileViewModel: ProfileViewModel by viewModels()
                App(
                    authViewModel = authViewModel,
                    camViewModel = camViewModel,
                    recipesViewModel = recipesViewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}
