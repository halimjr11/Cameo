package com.nurhaqhalim.cameo.core.data.remote.client

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nurhaqhalim.cameo.core.BuildConfig
import com.nurhaqhalim.cameo.core.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient(
    val authInterceptor: AuthInterceptor,
    val chuckerInterceptor: ChuckerInterceptor,
    val loggingInterceptor: HttpLoggingInterceptor
) {
    inline fun <reified I> create(): I {
        //okhttp
        val okhttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()

        return retrofit.create(I::class.java)
    }
}
