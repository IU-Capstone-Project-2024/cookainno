package com.cookainno.mobile.data.repository

import android.util.Log
import com.cookainno.mobile.data.Constants
import com.cookainno.mobile.data.model.GenRecipesResponse
import com.cookainno.mobile.data.model.GeneratedRecipes
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
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val generationService = Retrofit.Builder()
        .baseUrl(Constants.ML_URL)
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

    private fun parseRecipesFromJson(json: String): List<GeneratedRecipes> {
        val gson = Gson()
        val response = gson.fromJson(json, GenRecipesResponse::class.java)
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