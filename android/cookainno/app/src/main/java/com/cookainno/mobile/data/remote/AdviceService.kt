package com.cookainno.mobile.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AdviceService {
    @GET("/advice/{query}")
    suspend fun generateAdvice(@Query("query") type: String): Response<String>
}
