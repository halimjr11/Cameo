package com.nurhaqhalim.cameo.di

import com.nurhaqhalim.cameo.core.base.BaseModule
import com.nurhaqhalim.cameo.viewmodel.MainViewModel
import com.nurhaqhalim.cameo.viewmodel.PreLoginViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule : BaseModule {
    private val viewModelModules = module {
        single { PreLoginViewModel(get()) }
        single { MainViewModel(get()) }
    }

    override fun getModules(): List<Module> = listOf(viewModelModules)
}