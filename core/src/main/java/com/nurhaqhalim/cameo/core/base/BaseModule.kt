package com.nurhaqhalim.cameo.core.base

import org.koin.core.module.Module

interface BaseModule {
    fun getModules(): List<Module>
}