package com.halimjr11.cameo.domain.usecase

import com.halimjr11.cameo.domain.repository.CameoLocalRepository

class GetFavoriteUseCase(
    private val repository: CameoLocalRepository
) {
    suspend operator fun invoke() = repository.getFavoriteMovies()
}