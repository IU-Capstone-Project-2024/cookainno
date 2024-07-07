package com.cookainno.mobile.data.repository

import android.util.Log
import com.cookainno.mobile.data.Constants
import com.cookainno.mobile.data.model.UserDataRequest
import com.cookainno.mobile.data.model.UserDataResponse
import com.cookainno.mobile.data.remote.AdviceService
import com.cookainno.mobile.data.remote.AuthInterceptor
import com.cookainno.mobile.data.remote.RecomendationService
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RecomendationRepository(private val preferencesRepository: PreferencesRepository) {
    private var recomendationService: RecomendationService? = null
    private val adviceService = Retrofit.Builder()
            .baseUrl(Constants.ML_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build())
            .build()
            .create(AdviceService::class.java)

    fun initToken() {
        if (recomendationService == null) {
            recomendationService = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(
                    OkHttpClient().newBuilder()
                        .addInterceptor(AuthInterceptor(preferencesRepository))
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecomendationService::class.java)
        }
    }

    suspend fun putUserData(
        userId: Int,
        height: Int,
        weight: Int,
        date: String
    ): Result<Response<Unit>> {
        return try {
            val res = recomendationService?.updateInfo(
                UserDataRequest(
                    userId,
                    height,
                    weight,
                    date
                )
            )
            Log.d("UMPALUMPA", "putUserData: ${res?.code()}")
            if (res?.isSuccessful == true) {
                return Result.success(res)
            } else {
                return Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserData(userId: Int): Result<UserDataResponse> {
        return try {
            val response = recomendationService?.getUserInfo(userId)
            if (response?.isSuccessful == true && response.body() != null) {
                return Result.success(response.body()!!)
            } else {
                return Result.failure(Exception("Unsuccessful"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun generateAdvice(type: String): Result<String> {
        return try {
            val response = adviceService.generateAdvice(type)
            Log.d("UGLY BIDEN", "generateAdvice: ${response.body()}")
            if (response.isSuccessful && response.body() != null) {
                return Result.success(response.body()!!)
            } else {
                return Result.failure(Exception("Unsuccessful"))
            }
        } catch (e: Exception) {
            Log.d("UGLY BIDEN", "generateAdvice: ${e.message}")
            return Result.failure(e)
        }
    }
}