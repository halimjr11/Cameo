package com.nurhaqhalim.cameo.core.data.remote.service

import com.nurhaqhalim.cameo.core.data.remote.model.NowPlayingResponse
import com.nurhaqhalim.cameo.core.data.remote.model.PopularResponse
import com.nurhaqhalim.cameo.core.data.remote.model.TopRatedResponse
import com.nurhaqhalim.cameo.core.data.remote.model.UpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface ApiEndpoint {
    @GET("movie/now_playing")
    suspend fun fetchNowPlaying(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Locale.getDefault().language
    ): NowPlayingResponse

    @GET("movie/popular")
    suspend fun fetchPopular(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Locale.getDefault().language
    ): PopularResponse

    @GET("movie/top_rated")
    suspend fun fetchTopRated(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Locale.getDefault().language
    ): TopRatedResponse

    @GET("movie/upcoming")
    suspend fun fetchUpcoming(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Locale.getDefault().language
    ): UpcomingResponse
}