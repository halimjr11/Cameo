package com.halimjr11.cameo.data.impl

import com.halimjr11.cameo.common.DomainResult
import com.halimjr11.cameo.data.network.mapper.RemoteDataMapper
import com.halimjr11.cameo.data.network.service.CameoService
import com.halimjr11.cameo.data.utils.safeApiCall
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.domain.repository.CameoRemoteRepository

class CameoRemoteRepositoryImpl(
    private val service: CameoService,
    private val mapper: RemoteDataMapper
) : CameoRemoteRepository {
    override suspend fun getDiscoverMovies(): DomainResult<List<MovieDomain>> = safeApiCall {
        val response = service.getDiscoverMovies()
        mapper.mapMovieList(response.movies)
    }


    override suspend fun getMovieDetail(
        movieId: Int
    ): DomainResult<MovieDetailDomain> = safeApiCall {
        val response = service.getMovieDetail(movieId)
        mapper.mapMovieDetail(response)
    }

    override suspend fun getDetailCredit(
        movieId: Int
    ): DomainResult<List<MovieDetailDomain.CastDomain>> = safeApiCall {
        val response = service.getMovieCredits(movieId)
        mapper.mapMovieCredits(response.cast)
    }

}
