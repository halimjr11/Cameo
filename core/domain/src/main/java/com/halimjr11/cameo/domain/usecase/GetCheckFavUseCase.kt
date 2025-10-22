package com.halimjr11.cameo.domain.usecase

import com.halimjr11.cameo.domain.repository.CameoLocalRepository


class GetCheckFavUseCase(private val repository: CameoLocalRepository) {
    suspend operator fun invoke(movieId: Int) = repository.checkFavoriteMovie(movieId)
}