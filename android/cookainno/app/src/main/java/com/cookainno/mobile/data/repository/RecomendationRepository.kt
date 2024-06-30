package com.cookainno.mobile.data.repository

import android.util.Log
import com.cookainno.mobile.data.Constants
import com.cookainno.mobile.data.model.UserDataRequest
import com.cookainno.mobile.data.remote.AuthInterceptor
import com.cookainno.mobile.data.remote.RecomendationService
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class RecomendationRepository(private val preferencesRepository: PreferencesRepository) {
    private var recomendationService: RecomendationService? = null
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

    suspend fun putUserData(userId: Int, height: Int, weight: Int, date: String): Result<Response<Unit>> {
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
}