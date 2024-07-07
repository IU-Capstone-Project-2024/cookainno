package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.model.GeneratedRecipe
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.data.model.RecipeToAdd
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesService {
    @GET("example")
    suspend fun exampleCall(): Response<Unit>

    @GET("recipes/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Response<Recipe>

    @POST("recipes")
    suspend fun addNewRecipe(@Body recipe: RecipeToAdd): Response<Unit>

    @GET("recipes")
    suspend fun getRecipes(): Response<List<Recipe>>

    @GET("recipes/sorted-by-likes")
    suspend fun getRecipesSortedByLikes(
        @Query("page") skip: Int,
        @Query("size") size: Int
    ): Response<List<Recipe>>

    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("name") name: String,
        @Query("page") skip: Int,
        @Query("size") size: Int
    ): Response<List<Recipe>>

    @POST("recipes/{userId}/favorites/{recipeId}")
    suspend fun addFavouriteRecipe(
        @Path("userId") userId: Int,
        @Path("recipeId") recipeId: Int
    ): Response<Unit>

    @DELETE("recipes/{userId}/favorites/{recipeId}")
    suspend fun deleteFavouriteRecipe(
        @Path("userId") userId: Int,
        @Path("recipeId") recipeId: Int
    ): Response<Unit>

    @GET("recipes/{userId}/favorites")
    suspend fun getFavouriteRecipes(
        @Path("userId") userId: Int,
        @Query("page") skip: Int,
        @Query("size") size: Int,
        @Query("oldestFirst") oldestFirst: Boolean
    ): Response<List<Recipe>>

    @GET("recipes/{userId}/favorites/search")
    suspend fun searchFavouriteRecipes(
        @Path("userId") userId: Int,
        @Query("name") name: String,
        @Query("page") skip: Int,
        @Query("size") size: Int
    ): Response<List<Recipe>>
}