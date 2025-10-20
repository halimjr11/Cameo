package com.halimjr11.cameo.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.halimjr11.cameo.BuildConfig
import com.halimjr11.cameo.common.Constants
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.common.coroutines.impl.DefaultDispatcherProvider
import com.halimjr11.cameo.common.helper.DateFormatter
import com.halimjr11.cameo.common.helper.LanguageManager
import com.halimjr11.cameo.common.helper.ThemeManager
import com.halimjr11.cameo.data.impl.CameoLocalRepositoryImpl
import com.halimjr11.cameo.data.impl.CameoRemoteRepositoryImpl
import com.halimjr11.cameo.data.local.database.MovieDatabase
import com.halimjr11.cameo.data.local.mapper.LocalDataMapper
import com.halimjr11.cameo.data.local.mapper.impl.LocalDataMapperImpl
import com.halimjr11.cameo.data.network.interceptor.AuthInterceptor
import com.halimjr11.cameo.data.network.mapper.RemoteDataMapper
import com.halimjr11.cameo.data.network.mapper.impl.RemoteDataMapperImpl
import com.halimjr11.cameo.data.network.service.CameoService
import com.halimjr11.cameo.domain.repository.CameoLocalRepository
import com.halimjr11.cameo.domain.repository.CameoRemoteRepository
import com.halimjr11.cameo.navigation.CameoAppNavigation
import com.halimjr11.cameo.navigation.CameoNavigation
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object AppModule {
    private val coroutineModule = module {
        single<CoroutineDispatcherProvider> { DefaultDispatcherProvider() }
    }
    private val repositoryModule = module {
        single<CameoRemoteRepository> { CameoRemoteRepositoryImpl(get(), get()) }
        single<CameoLocalRepository> { CameoLocalRepositoryImpl(get(), get(), get()) }
    }
    private val mapperModule = module {
        single<RemoteDataMapper> { RemoteDataMapperImpl(get(), get()) }
        single<LocalDataMapper> { LocalDataMapperImpl(get()) }
    }
    private val dataHelperModule = module {
        single { DateFormatter() }
        single { LanguageManager() }
        single { ThemeManager(androidContext()) }
    }
    private val navigationModule = module {
        single<CameoNavigation> { CameoAppNavigation() }
    }
    private val interceptorModule = module {
        single {
            HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
        single {
            ChuckerInterceptor.Builder(androidContext())
                .collector(
                    ChuckerCollector(
                        context = androidContext(),
                        showNotification = true,
                        retentionPeriod = RetentionManager.Period.ONE_HOUR
                    )
                )
                .maxContentLength(250_000L)
                .alwaysReadResponseBody(true)
                .build()
        }
        single {
            AuthInterceptor()
        }
    }

    private val serviceModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .addInterceptor(get<ChuckerInterceptor>())
                .addInterceptor(get<AuthInterceptor>())
                .build()
        }

        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get<OkHttpClient>())
                .build()
        }

        single<CameoService> { get<Retrofit>().create(CameoService::class.java) }
    }

    private val databaseModule = module {
        single {
            Room.databaseBuilder(
                get(),
                MovieDatabase::class.java,
                Constants.DB_NAME
            ).fallbackToDestructiveMigration(dropAllTables = false).build()
        }

        single { get<MovieDatabase>().movieDao() }
    }

    fun getAppModules() = listOf(
        coroutineModule,
        dataHelperModule,
        repositoryModule,
        mapperModule,
        navigationModule,
        interceptorModule,
        serviceModule,
        databaseModule
    )
}