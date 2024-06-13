package com.cookainno.mobile.utilities

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri

class ImageUtility(private val activity: ComponentActivity) {

    var onImageTaken: ((Bitmap) -> Unit)? = null
    var onImageSelected: ((Uri) -> Unit)? = null

    private val startCamera = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            imageBitmap?.let { onImageTaken?.invoke(it) }
        }
    }

    private val startGallery = activity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { onImageSelected?.invoke(it) }
    }

    private val requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startCamera.launch(cameraIntent)
        } else {
            //
        }
    }

    fun checkPermissionsAndStartCamera() {
        when {
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startCamera.launch(cameraIntent)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    fun checkPermissionsAndStartGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startGallery.launch("image/*")
    }
}
