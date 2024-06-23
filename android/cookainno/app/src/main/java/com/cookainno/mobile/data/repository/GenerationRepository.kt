package com.cookainno.mobile.data.repository

import com.cookainno.mobile.data.Constants
import com.cookainno.mobile.data.model.GeneratedRecipes
import com.cookainno.mobile.data.remote.GenerationService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GenerationRepository {
    private val generationService = Retrofit.Builder()
        .baseUrl(Constants.MISTRAL_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GenerationService::class.java)

    suspend fun generateRecipes(ingredients: List<String>): Result<List<GeneratedRecipes>> {
        return try {
            Result.success(generationService.generateRecipe(ingredients).body()!!)
        } catch (e:Exception) {
            Result.failure(e)
        }
    }
}