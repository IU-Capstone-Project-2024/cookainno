package com.cookainno.mobile.data.repository

import com.cookainno.mobile.BuildConfig
import com.cookainno.mobile.data.model.ConfirmationRequest
import com.cookainno.mobile.data.model.ErrorResponse
import com.cookainno.mobile.data.model.LoginRequest
import com.cookainno.mobile.data.model.LoginResponse
import com.cookainno.mobile.data.model.RegistrationRequest
import com.cookainno.mobile.data.model.RegistrationResponse
import com.cookainno.mobile.data.remote.AuthService
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class AuthRepository(private val preferencesRepository: PreferencesRepository) {

    private var authService: AuthService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthService::class.java)

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<RegistrationResponse> {
        return try {
            val response = authService.registerUser(RegistrationRequest(username, email, password))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorResult = when (response.code()) {
                    400 -> {
                        val errorResponse = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                        errorResponse.exception_message
                    }
                    409 -> "A user with the same username or email already exists."
                    else -> "Unknown Error. Try again later."
                }
                Result.failure(Exception(errorResult))
            }
        } catch (e: Exception) {
            if (e is IOException) {
                Result.failure(Exception("Please check your internet connection."))
            } else {
                Result.failure(e)
            }
        }
    }

    suspend fun confirm(email: String, confirmationCode: String): Result<String> {
        return try {
            val response = authService.confirmEmail(ConfirmationRequest(email, confirmationCode))
            if (response.isSuccessful) {
                Result.success("OK")
            } else {
                val errorResult = when (response.code()) {
                    400 -> "Invalid code."
                    404 -> "Not found."
                    else -> "Unknown Error. Try again later."
                }
                Result.failure(Exception(errorResult))
            }
        } catch (e: Exception) {
            if (e is IOException) {
                Result.failure(Exception("Please check your internet connection."))
            } else {
                Result.failure(e)
            }
        }
    }

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val response = authService.loginUser(LoginRequest(username, password))
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.token?.let { response.body()!!.userId?.let { it1 ->
                    preferencesRepository.saveLoginData(it,
                        it1
                    )
                } }
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Boolean {
        return try {
            preferencesRepository.removeLoginData()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserID(): Int {
        return preferencesRepository.getUserIdFlow().first() ?: -1
    }
}
