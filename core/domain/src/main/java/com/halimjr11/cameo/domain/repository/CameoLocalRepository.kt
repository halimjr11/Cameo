package com.halimjr11.cameo.domain.repository

import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface CameoLocalRepository {
    suspend fun getFavoriteMovies(): Flow<List<MovieDomain>>
    suspend fun checkFavoriteMovie(movieId: Int): Boolean
    suspend fun insertFavoriteMovie(movie: MovieDetailDomain)
    suspend fun deleteFavoriteMovie(movie: MovieDetailDomain)
    suspend fun clearFavoriteMovies()

}