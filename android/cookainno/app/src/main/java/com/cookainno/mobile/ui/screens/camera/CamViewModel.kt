package com.cookainno.mobile.ui.screens.camera

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cookainno.mobile.utilities.ImageUtility

class CamViewModel(private val imageUtility: ImageUtility): ViewModel() {

    private val _imageBitmap = MutableLiveData<Bitmap>()
    val imageBitmap: LiveData<Bitmap> = _imageBitmap

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> = _imageUri

    init {
        imageUtility.onImageTaken = { bitmap ->
            _imageBitmap.postValue(bitmap)
        }

        imageUtility.onImageSelected = { uri ->
            _imageUri.postValue(uri)
        }
    }

    fun runCamera() {
        imageUtility.checkPermissionsAndStartCamera()
    }

    fun runGallery() {
        imageUtility.checkPermissionsAndStartGallery()
    }
}