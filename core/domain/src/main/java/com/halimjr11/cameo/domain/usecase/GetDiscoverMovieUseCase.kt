package com.halimjr11.cameo.domain.usecase

import com.halimjr11.cameo.common.DomainResult
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.domain.repository.CameoRemoteRepository
import kotlinx.coroutines.withContext

class GetDiscoverMovieUseCase(
    private val remoteRepository: CameoRemoteRepository,
    private val dispatcher: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(): DomainResult<Pair<List<MovieDomain>, List<MovieDomain>>> =
        withContext(dispatcher.io) {
            val result = remoteRepository.getDiscoverMovies()
            when (result) {
                is DomainResult.Success -> {
                    val discover = result.data.take(5)
                    val recommended = result.data.drop(5)
                    DomainResult.Success(Pair(discover, recommended))
                }

                is DomainResult.Error -> DomainResult.Error(result.message, result.code)
            }
        }
}