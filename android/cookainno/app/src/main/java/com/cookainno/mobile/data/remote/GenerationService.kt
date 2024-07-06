package com.cookainno.mobile.data.remote

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface GenerationService {
    @GET("/ingredients/")
    suspend fun generateRecipe(@Query("items") items: List<String>): Response<String>

    @Multipart
    @POST("/detect/")
    suspend fun detectIngredients(@Part file: MultipartBody.Part): Response<List<String>>
}
