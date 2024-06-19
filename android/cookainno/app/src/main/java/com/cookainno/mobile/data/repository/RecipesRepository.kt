package com.cookainno.mobile.data.repository

import com.cookainno.mobile.data.Constants
import com.cookainno.mobile.data.remote.AuthInterceptor
import com.cookainno.mobile.data.remote.AuthService
import com.cookainno.mobile.data.remote.RecipesService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
}