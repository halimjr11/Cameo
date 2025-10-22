package com.halimjr11.cameo.domain.usecase

import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.repository.CameoLocalRepository

class GetToggleFavoriteUseCase(private val repository: CameoLocalRepository) {
    suspend operator fun invoke(movie: MovieDetailDomain, isFavorite: Boolean) {
        if (isFavorite) {
            repository.deleteFavoriteMovie(movie)
        } else {
            repository.insertFavoriteMovie(movie)
        }
    }
}