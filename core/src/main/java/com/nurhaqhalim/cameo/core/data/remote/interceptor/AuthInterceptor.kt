package com.nurhaqhalim.cameo.core.data.remote.interceptor

import com.nurhaqhalim.cameo.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = request
            .newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            .build()
        return chain.proceed(modifiedRequest)

    }
}
