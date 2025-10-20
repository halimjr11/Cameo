package com.halimjr11.cameo.data.network.interceptor

import com.halimjr11.cameo.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = request.newBuilder().apply {
            addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
        }.build()
        return chain.proceed(modifiedRequest)
    }
}