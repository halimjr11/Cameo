package com.halimjr11.cameo.domain.repository

import com.halimjr11.cameo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface CameoLocalRepository {
    suspend fun getFavoriteMovies(): Flow<List<MovieDomain>>
}