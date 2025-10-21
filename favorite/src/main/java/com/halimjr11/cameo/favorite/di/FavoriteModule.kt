package com.halimjr11.cameo.favorite.di

import com.halimjr11.cameo.domain.usecase.GetFavoriteUseCase
import com.halimjr11.cameo.favorite.FavoriteActivity
import com.halimjr11.cameo.favorite.viewmodel.FavoriteViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadFavoriteModule() {
    val favoriteModule = module {
        scope<FavoriteActivity> { scoped { FavoriteViewModel(get()) } }
    }
    val useCase = module {
        scope<FavoriteActivity> { scoped { GetFavoriteUseCase(get()) } }
    }
    loadKoinModules(listOf(useCase, favoriteModule))
}
