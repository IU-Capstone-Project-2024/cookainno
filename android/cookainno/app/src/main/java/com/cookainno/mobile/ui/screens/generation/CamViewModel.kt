package com.cookainno.mobile.ui.screens.generation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cookainno.mobile.utilities.ImageUtility
import java.io.File

class CamViewModel(private val imageUtility: ImageUtility): ViewModel() {

    private val _camFile = MutableLiveData<File?>()
    val fileFromCam: MutableLiveData<File?> = _camFile

    private val _galleryFile = MutableLiveData<File?>()
    val fileFromGallery: LiveData<File?> = _galleryFile

    init {
        imageUtility.onImageTaken = { uri ->
            _camFile.postValue(uri)
        }

        imageUtility.onImageSelected = { uri ->
            _galleryFile.postValue(uri)
        }
    }

    fun resetImages() {
        _camFile.postValue(null)
        _galleryFile.postValue(null)
    }

    fun runCamera() {
        imageUtility.checkPermissionsAndStartCamera()
    }

    fun runGallery() {
        imageUtility.checkPermissionsAndStartGallery()
    }
}