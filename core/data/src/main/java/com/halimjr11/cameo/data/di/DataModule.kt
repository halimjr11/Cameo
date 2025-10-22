package com.halimjr11.cameo.data.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.halimjr11.cameo.common.Constants
import com.halimjr11.cameo.data.BuildConfig
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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

object DataModule {
    private val repositoryModule = module {
        single<CameoRemoteRepository> { CameoRemoteRepositoryImpl(get(), get()) }
        single<CameoLocalRepository> { CameoLocalRepositoryImpl(get(), get(), get()) }
    }
    private val mapperModule = module {
        single<RemoteDataMapper> { RemoteDataMapperImpl(get(), get()) }
        single<LocalDataMapper> { LocalDataMapperImpl(get()) }
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
            val hostName = "api.github.com"

            val certificatePinner = CertificatePinner.Builder()
                .add(hostName, BuildConfig.CERT_1)
                .add(hostName, BuildConfig.CERT_2)
                .add(hostName, BuildConfig.CERT_3)
                .build()

            OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
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
            val passphrase: ByteArray = SQLiteDatabase.getBytes("cameo_secure_db".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                get(),
                MovieDatabase::class.java,
                Constants.DB_NAME
            ).fallbackToDestructiveMigration(
                dropAllTables = true
            ).openHelperFactory(factory).build()
        }

        single { get<MovieDatabase>().movieDao() }
    }

    /**
     * Returns a list of modules required for the data layer.
     *
     * @return a list of modules required for the data layer.
     */
    fun getDataModule(): List<Module> = listOf(
        repositoryModule,
        mapperModule,
        interceptorModule,
        serviceModule,
        databaseModule
    )
}