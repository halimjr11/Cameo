package com.halimjr11.cameo.view.feature.detail.di

import com.halimjr11.cameo.domain.usecase.GetCheckFavUseCase
import com.halimjr11.cameo.domain.usecase.GetDetailUseCase
import com.halimjr11.cameo.domain.usecase.GetToggleFavoriteUseCase
import com.halimjr11.cameo.view.feature.detail.MovieDetailFragment
import com.halimjr11.cameo.view.feature.detail.viewmodel.DetailViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadDetailModule() {
    val viewModel = module {
        scope<MovieDetailFragment> { scoped { DetailViewModel(get(), get(), get(), get()) } }
    }
    val useCase = module {
        scope<MovieDetailFragment> { scoped { GetDetailUseCase(get(), get()) } }
        scope<MovieDetailFragment> { scoped { GetCheckFavUseCase(get()) } }
        scope<MovieDetailFragment> { scoped { GetToggleFavoriteUseCase(get()) } }
    }
    loadKoinModules(listOf(useCase, viewModel))
}