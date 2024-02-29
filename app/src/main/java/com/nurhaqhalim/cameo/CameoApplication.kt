package com.nurhaqhalim.cameo

import android.app.Application
import com.nurhaqhalim.cameo.core.di.CoreModule
import com.nurhaqhalim.cameo.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CameoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CameoApplication)
            androidLogger(level = Level.NONE)
            modules(AppModule.getModules())
            modules(CoreModule.getModules())
        }
    }
}