package com.halimjr11.cameo.view.feature.settings.di

import com.halimjr11.cameo.view.feature.settings.SettingsFragment
import com.halimjr11.cameo.view.feature.settings.viewmodel.SettingsViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun loadSettingModule() {
    val viewModel = module {
        scope<SettingsFragment> { scoped { SettingsViewModel(get(), get(), get()) } }
    }
    loadKoinModules(viewModel)
}
