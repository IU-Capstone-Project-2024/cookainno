package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.model.ConfirmationRequest
import com.cookainno.mobile.data.model.LoginRequest
import com.cookainno.mobile.data.model.LoginResponse
import com.cookainno.mobile.data.model.RegistrationRequest
import com.cookainno.mobile.data.model.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/sign-up")
    suspend fun registerUser(@Body registrationRequest: RegistrationRequest): Response<RegistrationResponse>

    @POST("/auth/confirm-email")
    suspend fun confirmEmail(@Body confirmationRequest: ConfirmationRequest): Response<Unit>

    @POST("/auth/sign-in")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
