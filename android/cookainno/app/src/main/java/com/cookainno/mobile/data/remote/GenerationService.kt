package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.model.GeneratedRecipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenerationService {
    @GET("/ingredients/")
    suspend fun generateRecipe(@Query("items") items: List<String>): Response<List<GeneratedRecipes>>
}