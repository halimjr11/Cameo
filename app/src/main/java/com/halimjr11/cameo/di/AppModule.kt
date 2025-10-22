package com.halimjr11.cameo.di

import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.common.coroutines.impl.DefaultDispatcherProvider
import com.halimjr11.cameo.common.helper.DateFormatter
import com.halimjr11.cameo.common.helper.LanguageManager
import com.halimjr11.cameo.common.helper.ThemeManager
import com.halimjr11.cameo.navigation.CameoAppNavigation
import com.halimjr11.cameo.navigation.CameoNavigation
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object AppModule {
    private val coroutineModule = module {
        single<CoroutineDispatcherProvider> { DefaultDispatcherProvider() }
    }
    private val dataHelperModule = module {
        single { DateFormatter() }
        single { LanguageManager() }
        single { ThemeManager(androidContext()) }
    }
    private val navigationModule = module {
        single<CameoNavigation> { CameoAppNavigation() }
    }

    fun getAppModules() = listOf(
        coroutineModule,
        dataHelperModule,
        navigationModule,
    )
}