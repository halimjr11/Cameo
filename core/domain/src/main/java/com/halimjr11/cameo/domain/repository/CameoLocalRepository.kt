package com.halimjr11.cameo.domain.repository

import com.halimjr11.cameo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface CameoLocalRepository {
    suspend fun getFavoriteMovies(): Flow<List<MovieDomain>>
    suspend fun checkFavoriteMovie(movieId: Int): Boolean
    suspend fun insertFavoriteMovie(movie: MovieDomain)
    suspend fun deleteFavoriteMovie(movie: MovieDomain)
    suspend fun clearFavoriteMovies()

}