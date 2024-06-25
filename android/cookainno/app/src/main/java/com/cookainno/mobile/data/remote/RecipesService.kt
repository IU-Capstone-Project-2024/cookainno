package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.model.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesService {
    @GET("/example")
    suspend fun exampleCall(): Response<Unit>

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Response<Recipe>

    @GET("recipes")
    suspend fun getRecipes(): Response<List<Recipe>>
}