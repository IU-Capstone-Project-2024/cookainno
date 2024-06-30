package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.model.UserDataRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface RecomendationService {
    @PUT("/users")
    suspend fun updateInfo(@Body userDataRequest: UserDataRequest): Response<Unit>
}
