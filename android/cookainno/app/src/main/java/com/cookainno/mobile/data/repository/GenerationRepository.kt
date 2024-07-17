package com.cookainno.mobile.data.repository

import android.util.Log
import com.cookainno.mobile.BuildConfig
import com.cookainno.mobile.data.model.GenRecipesResponse
import com.cookainno.mobile.data.model.GeneratedRecipe
import com.cookainno.mobile.data.remote.GenerationService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class GenerationRepository {
    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val generationService = Retrofit.Builder()
        .baseUrl(BuildConfig.ML_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(GenerationService::class.java)

    suspend fun generateRecipes(ingredients: List<String>): Result<GenRecipesResponse> {
        return try {
            val response = generationService.generateRecipe(ingredients)
            if (response.isSuccessful) {
                val json = response.body()
                val recipes = parseRecipesFromJson(json!!.replace("\\", ""))
                Result.success(GenRecipesResponse(recipes))
            } else {
                Result.failure(Exception("Failed to generate recipes"))
            }
        } catch (e: Exception) {
            Log.d("POLKA", "generateRecipes: ${e.message}")
            Result.failure(e)
        }
    }

    private fun parseRecipesFromJson(json: String): List<GeneratedRecipe> {
        val gson = Gson()
        val response = gson.fromJson(json, GenRecipesResponse::class.java)
        // placeholder: remove when images will be generated
//        for (i in response.recipes) {
//            i.image_url = "https://png.pngitem.com/pimgs/s/391-3918617_clip-art-hd-png-download.png"
//        }
        return response.recipes
    }

    suspend fun detectIngredients(file: File): Result<List<String>> {
        return try {
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = requestFile.let { MultipartBody.Part.createFormData("file", file.name, it) }
            val response = body.let { generationService.detectIngredients(it) }
            Log.d("VOID SEX", "detectIngredients: ${response.body()}")
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to detect"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}