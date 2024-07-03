package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.model.UserDataRequest
import com.cookainno.mobile.data.model.UserDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecomendationService {
    @PUT("/users")
    suspend fun updateInfo(@Body userDataRequest: UserDataRequest): Response<Unit>

    @GET("users/{id}")
    suspend fun getUserInfo(@Path("id") id: Int): Response<UserDataResponse>
}
