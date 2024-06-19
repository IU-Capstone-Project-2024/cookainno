package com.cookainno.mobile.ui.screens.recipes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cookainno.mobile.ui.screens.camera.CamViewModel

@Composable
fun RecipesScreen(camViewModel: CamViewModel, recipesViewModel: RecipesViewModel) {
    val res by recipesViewModel.okExample.collectAsState()
    recipesViewModel.example()
    Text(text = "Test auth call: ${if (res) "Res OK" else "Res FAIL"}")
}
