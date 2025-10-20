package com.halimjr11.cameo.favorite.di

import com.halimjr11.cameo.favorite.viewmodel.FavoriteViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadFavoriteModule() {
    val favoriteModule = module {
        single { FavoriteViewModel(get()) }
    }
    loadKoinModules(favoriteModule)
}
