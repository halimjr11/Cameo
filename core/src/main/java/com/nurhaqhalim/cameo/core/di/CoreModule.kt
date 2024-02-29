package com.nurhaqhalim.cameo.core.di

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.ktx.storage
import com.nurhaqhalim.cameo.core.base.BaseModule
import com.nurhaqhalim.cameo.core.data.CameoRepository
import com.nurhaqhalim.cameo.core.data.local.LocalDataSource
import com.nurhaqhalim.cameo.core.data.local.sharedpreference.SharedPreferenceHelper
import com.nurhaqhalim.cameo.core.data.local.sharedpreference.SharedPreferenceImpl
import com.nurhaqhalim.cameo.core.data.remote.RemoteDataSource
import com.nurhaqhalim.cameo.core.data.remote.client.NetworkClient
import com.nurhaqhalim.cameo.core.data.remote.interceptor.AuthInterceptor
import com.nurhaqhalim.cameo.core.data.remote.service.ApiEndpoint
import com.nurhaqhalim.cameo.core.domain.repository.CameoRepositoryImpl
import com.nurhaqhalim.cameo.core.domain.usecase.CameoInteractor
import com.nurhaqhalim.cameo.core.domain.usecase.CameoUseCase
import com.nurhaqhalim.cameo.core.utils.Constant
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreModule : BaseModule {
    private val sharedPreferenceModules = module {
        single<SharedPreferences> {
            androidContext().getSharedPreferences(Constant.prefName, Context.MODE_PRIVATE)
        }
        single<SharedPreferenceHelper> { SharedPreferenceImpl(get()) }
    }
    private val firebaseModules = module {
        single {
            Firebase.auth
        }
        single {
            Firebase.analytics
        }
        single {
            Firebase.messaging
        }
        single {
            Firebase.remoteConfig
        }
        single {
            Firebase.storage
        }
    }
    private val networkModules = module {
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { AuthInterceptor() }
        single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }
        single { NetworkClient(get(), get(), get()) }
        single<ApiEndpoint> { get<NetworkClient>().create() }
    }

    private val databaseModules = module {

    }
    private val dataSourceModules = module {
        single { RemoteDataSource(get(), get(), get(), get(), get()) }
        single { LocalDataSource(get()) }
    }
    private val repositoryModules = module {
        single<CameoRepository> { CameoRepositoryImpl(get(), get()) }
    }
    private val useCaseModule = module {
        single<CameoUseCase> { CameoInteractor(get()) }
    }

    override fun getModules(): List<Module> = listOf(
        sharedPreferenceModules,
        firebaseModules,
        networkModules,
        databaseModules,
        dataSourceModules,
        repositoryModules,
        useCaseModule
    )
}