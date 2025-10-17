package com.halimjr11.cameo.data.impl

import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.domain.repository.CameoLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CameoLocalRepositoryImpl() : CameoLocalRepository {
    override suspend fun getFavoriteMovies(): Flow<List<MovieDomain>> {
        return flow { emit(listOf<MovieDomain>()) }
    }
}