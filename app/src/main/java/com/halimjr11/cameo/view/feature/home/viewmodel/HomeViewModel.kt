package com.halimjr11.cameo.view.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.cameo.common.DomainResult
import com.halimjr11.cameo.common.UiState
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.domain.usecase.GetDiscoverMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase,
    private val dispatcher: CoroutineDispatcherProvider
) : ViewModel() {
    private val _homeMovie: MutableStateFlow<UiState<Pair<List<MovieDomain>, List<MovieDomain>>>> =
        MutableStateFlow(UiState.Loading)
    val homeMovie: StateFlow<UiState<Pair<List<MovieDomain>, List<MovieDomain>>>> = _homeMovie

    init {
        getHomeMovie()
    }

    fun getHomeMovie() = viewModelScope.launch(dispatcher.io) {
        val result = getDiscoverMovieUseCase()
        _homeMovie.value = when (result) {
            is DomainResult.Success -> {
                UiState.Success(result.data)
            }

            is DomainResult.Error -> {
                UiState.Error(result.message)
            }
        }
    }
}