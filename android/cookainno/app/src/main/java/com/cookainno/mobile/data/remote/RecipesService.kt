package com.cookainno.mobile.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface RecipesService {
    @GET("/example")
    suspend fun exampleCall(): Response<Unit>
}