package com.cookainno.mobile.ui.screens.camera

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(camViewModel: CamViewModel) {
    val imageBitmap by camViewModel.imageBitmap.observeAsState()
    val imageUri by camViewModel.imageUri.observeAsState()
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
        Spacer(modifier = Modifier.height(20.dp))
        imageBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        imageUri?.let { uri ->
            Image(painter = rememberAsyncImagePainter(uri), contentDescription = "Selected image")
        }
    }
}