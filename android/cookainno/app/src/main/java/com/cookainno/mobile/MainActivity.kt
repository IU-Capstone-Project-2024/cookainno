package com.cookainno.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cookainno.mobile.data.repository.PreferencesRepository
import com.cookainno.mobile.ui.App
import com.cookainno.mobile.ui.screens.auth.UserViewModel
import com.cookainno.mobile.ui.screens.camera.CamViewModel
import com.cookainno.mobile.ui.screens.recipes.RecipesViewModel
import com.cookainno.mobile.ui.theme.CookainnoTheme
import com.cookainno.mobile.utilities.ImageUtility

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cameraUtility = ImageUtility(this)
        val preferencesRepository = PreferencesRepository(this)
        setContent {
            CookainnoTheme {
                val authViewModel = UserViewModel(preferencesRepository)
                val camViewModel = CamViewModel(imageUtility = cameraUtility)
                val recipesViewModel = RecipesViewModel(preferencesRepository)
                App(
                    authViewModel = authViewModel,
                    camViewModel = camViewModel,
                    recipesViewModel = recipesViewModel
                )
            }
        }
    }
}
