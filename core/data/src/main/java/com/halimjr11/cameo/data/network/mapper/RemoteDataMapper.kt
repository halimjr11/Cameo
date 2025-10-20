package com.halimjr11.cameo.data.network.mapper

import com.halimjr11.cameo.data.network.model.MovieCreditResponse
import com.halimjr11.cameo.data.network.model.MovieDetailResponse
import com.halimjr11.cameo.data.network.model.MovieListResponse
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.model.MovieDomain

interface RemoteDataMapper {
    suspend fun mapMovieList(movies: List<MovieListResponse.Movie?>?): List<MovieDomain>
    suspend fun mapMovieCredits(movies: List<MovieCreditResponse.Cast?>?): List<MovieDetailDomain.CastDomain>
    suspend fun mapMovieDetail(detailResponse: MovieDetailResponse?): MovieDetailDomain
}