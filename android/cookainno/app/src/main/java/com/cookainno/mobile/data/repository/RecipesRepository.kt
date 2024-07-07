package com.cookainno.mobile.data.repository

import android.util.Log
import com.cookainno.mobile.data.Constants
import com.cookainno.mobile.data.model.GeneratedRecipe
import com.cookainno.mobile.data.model.Recipe
import com.cookainno.mobile.data.model.RecipeToAdd
import com.cookainno.mobile.data.remote.AuthInterceptor
import com.cookainno.mobile.data.remote.RecipesService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class RecipesRepository(private val preferencesRepository: PreferencesRepository) {
    private var recipesService: RecipesService? = null
    fun initToken() {
        if (recipesService == null) {
            recipesService = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(
                    OkHttpClient().newBuilder()
                        .addInterceptor(AuthInterceptor(preferencesRepository))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipesService::class.java)
        }
    }

    suspend fun example(): Boolean {
        return recipesService?.exampleCall()?.isSuccessful?:false
    }

    suspend fun getRecipe(id: Int): Result<Recipe> {
        return try {
            Result.success(recipesService?.getRecipe(id)?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRecipes(): Result<List<Recipe>> {
        return try {
            Result.success(recipesService?.getRecipes()?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addGeneratedRecipe(recipe: RecipeToAdd): Result<Unit> {
        try {
            val resp = recipesService?.addNewRecipe(recipe)
            Log.d("DUBMBLEDORE", "addGeneratedRecipe: ${resp?.code()}")
            return if (resp?.isSuccessful == true) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error while adding recipe to DB"))
            }
        } catch (e: Exception) {
            Log.d("DUBMBLEDORE", "addGeneratedRecipe: ${e.message}")
            return Result.failure(e)
        }
    }

    suspend fun getRecipesSortedByLikes(skip: Int, pageSize: Int): Result<List<Recipe>> {
        return try {
            Result.success(recipesService?.getRecipesSortedByLikes(skip, pageSize)?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchRecipes(name: String, skip: Int, pageSize: Int): Result<List<Recipe>> {
        return try {
            Result.success(recipesService?.searchRecipes(name, skip, pageSize)?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addFavouriteRecipe(userId: Int, recipeId: Int): Result<Unit> {
        return try {
            Result.success(recipesService?.addFavouriteRecipe(userId, recipeId)?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteFavouriteRecipe(userId: Int, recipeId: Int): Result<Unit> {
        return try {
            Result.success(recipesService?.deleteFavouriteRecipe(userId, recipeId)?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFavouriteRecipes(userId: Int, skip: Int, pageSize: Int, oldestFirst: Boolean): Result<List<Recipe>> {
        return try {
            Result.success(recipesService?.getFavouriteRecipes(userId, skip, pageSize, oldestFirst)?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchFavouriteRecipes(userId: Int, name: String, skip: Int, pageSize: Int): Result<List<Recipe>> {
        return try {
            val resp = recipesService?.searchFavouriteRecipes(userId, name, skip, pageSize)
            Log.d("CHAPMAN", "searchFavouriteRecipes: ${resp} $userId $name $skip $pageSize")
            Result.success(resp?.body()!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}