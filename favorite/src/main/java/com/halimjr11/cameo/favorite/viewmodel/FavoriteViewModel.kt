package com.halimjr11.cameo.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.cameo.common.UiState
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.domain.repository.CameoLocalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

class FavoriteViewModel(
    private val localRepository: CameoLocalRepository
) : ViewModel() {

    val favoriteMovie: StateFlow<UiState<List<MovieDomain>>> = runBlocking {
        localRepository
            .getFavoriteMovies()
            .transform {
                if (it.isNotEmpty()) {
                    emit(UiState.Success(it))
                } else {
                    emit(UiState.Error("No favorite movies"))
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState.Loading
            )
    }

}