package com.halimjr11.cameo.view.feature.home.di

import com.halimjr11.cameo.domain.usecase.GetDiscoverMovieUseCase
import com.halimjr11.cameo.view.feature.home.HomeFragment
import com.halimjr11.cameo.view.feature.home.viewmodel.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadHomeModule() {
    val viewModel = module {
        scope<HomeFragment> { scoped { HomeViewModel(get(), get()) } }
    }
    val useCase = module {
        scope<HomeFragment> { scoped { GetDiscoverMovieUseCase(get(), get()) } }
    }
    loadKoinModules(listOf(useCase, viewModel))
}