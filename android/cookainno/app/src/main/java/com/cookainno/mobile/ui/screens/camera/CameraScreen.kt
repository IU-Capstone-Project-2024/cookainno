package com.cookainno.mobile.ui.screens.camera

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cookainno.mobile.ui.screens.ingredients.CamViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(camViewModel: CamViewModel) {
    Column {
        Text(text = "Choose the image of fridge to recognize:")
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            camViewModel.runCamera()
        }) {
            Text(text = "Take a photo")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            camViewModel.runGallery()
        }) {
            Text(text = "Choose from gallery")
        }
    }
}
