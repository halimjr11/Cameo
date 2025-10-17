package com.halimjr11.cameo.data.network.service

import com.halimjr11.cameo.data.network.model.MovieCreditResponse
import com.halimjr11.cameo.data.network.model.MovieDetailResponse
import com.halimjr11.cameo.data.network.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CameoService {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en"
    ): MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): MovieCreditResponse
}