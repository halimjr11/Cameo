package com.halimjr11.cameo.domain.usecase

import com.halimjr11.cameo.common.DomainResult
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.repository.CameoRemoteRepository
import kotlinx.coroutines.withContext

class GetDetailUseCase(
    private val remoteRepository: CameoRemoteRepository,
    private val dispatcher: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(
        id: Int
    ): DomainResult<MovieDetailDomain> = withContext(dispatcher.io) {
        val detail = remoteRepository.getMovieDetail(id)
        val cast = remoteRepository.getDetailCredit(id)
        if (detail is DomainResult.Success && cast is DomainResult.Success) {
            DomainResult.Success(detail.data.copy(cast = cast.data))
        } else {
            DomainResult.Error("Failed to load data. Please try again.")
        }
    }
}