package com.halimjr11.cameo.domain.repository

import com.halimjr11.cameo.common.DomainResult
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.model.MovieDomain

interface CameoRemoteRepository {
    suspend fun getDiscoverMovies(): DomainResult<List<MovieDomain>>
    suspend fun getMovieDetail(movieId: Int): DomainResult<MovieDetailDomain>

    suspend fun getDetailCredit(movieId: Int): DomainResult<List<MovieDetailDomain.CastDomain>>
}