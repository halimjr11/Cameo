package com.halimjr11.cameo

import android.app.Application
import com.halimjr11.cameo.data.di.DataModule
import com.halimjr11.cameo.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CameoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CameoApp)
            modules(AppModule.getAppModules() + DataModule.getDataModule())
        }
    }
}