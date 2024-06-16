package com.cookainno.mobile.data.remote

import com.cookainno.mobile.data.repository.PreferencesRepository
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.runBlocking

class AuthInterceptor(private val preferencesRepository: PreferencesRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { preferencesRepository.getTokenFlow().first() }
        val requestBuilder = chain.request().newBuilder()
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}
