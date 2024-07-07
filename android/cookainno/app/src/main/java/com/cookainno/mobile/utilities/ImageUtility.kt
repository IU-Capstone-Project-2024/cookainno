package com.cookainno.mobile.utilities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.File

class ImageUtility(private val activity: ComponentActivity) {

    var onImageTaken: ((File) -> Unit)? = null
    var onImageSelected: ((File) -> Unit)? = null

    private val startCamera = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as? Bitmap
            imageBitmap?.let { getFileFromUri(getImageUri(it))?.let { it1 ->
                onImageTaken?.invoke(
                    it1
                )
            } }
        }
    }

    private val startGallery = activity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { getFileFromUri(it)?.let { it1 -> onImageSelected?.invoke(it1) } }
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

    private fun getImageUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(activity.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    private fun getFileFromUri(uri: Uri): File? {
        return try {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = activity.contentResolver.query(uri, filePathColumn, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                val filePath = it.getString(columnIndex)
                File(filePath)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun checkPermissionsAndStartGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startGallery.launch("image/*")
    }
}
