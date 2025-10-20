package com.halimjr11.cameo.view.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.cameo.common.DomainResult
import com.halimjr11.cameo.common.UiState
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.repository.CameoLocalRepository
import com.halimjr11.cameo.domain.usecase.GetDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val localRepository: CameoLocalRepository,
    private val getDetailUseCase: GetDetailUseCase,
    private val dispatchers: CoroutineDispatcherProvider
) : ViewModel() {
    private val _detailMovie: MutableStateFlow<UiState<MovieDetailDomain>> =
        MutableStateFlow(UiState.Loading)
    val detailMovie: StateFlow<UiState<MovieDetailDomain>> = _detailMovie

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun checkFavorite(id: Int) = viewModelScope.launch(dispatchers.io) {
        val result = localRepository.checkFavoriteMovie(id)
        _isFavorite.value = result
    }


    fun getDetailMovie(id: Int) = viewModelScope.launch(dispatchers.io) {
        checkFavorite(id)
        val result = getDetailUseCase(id)
        _detailMovie.value = when (result) {
            is DomainResult.Success -> {
                UiState.Success(result.data)
            }

            is DomainResult.Error -> {
                UiState.Error(result.message)
            }
        }
    }

    fun toggleFavorite(
        movieDetailDomain: MovieDetailDomain
    ) = viewModelScope.launch(dispatchers.io) {
        if (_isFavorite.value) {
            localRepository.deleteFavoriteMovie(movieDetailDomain)
        } else {
            localRepository.insertFavoriteMovie(movieDetailDomain)
        }
        _isFavorite.value = !_isFavorite.value
    }

}