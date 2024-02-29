package com.nurhaqhalim.cameo.core.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.nurhaqhalim.cameo.core.data.CameoRepository
import com.nurhaqhalim.cameo.core.data.local.LocalDataSource
import com.nurhaqhalim.cameo.core.data.remote.RemoteDataSource
import com.nurhaqhalim.cameo.core.data.remote.model.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.FileInputStream

class CameoRepositoryImpl(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource
) : CameoRepository {
    override fun clearSessionData() = localDataSource.clearSessionData()
    override fun getCurrentUser(): FirebaseUser? = remoteDataSource.getCurrentUser()
    override fun getLanguage(): Boolean = localDataSource.getLanguageState()
    override fun getOnboardingState(): Boolean = localDataSource.getOnboardingState()
    override fun getTheme(): Boolean = localDataSource.getThemeState()
    override fun getUID(): String = localDataSource.getUID()
    override fun fetchLogout() = remoteDataSource.fetchLogout()
    override suspend fun fetchNowPlaying(page: Int, language: String): Flow<List<MoviesResponse>> =
        flow {
            emit(remoteDataSource.fetchNowPlaying(page, language).results)
        }.flowOn(Dispatchers.IO)

    override suspend fun fetchPopular(page: Int, language: String): Flow<List<MoviesResponse>> =
        flow {
            emit(remoteDataSource.fetchPopular(page, language).results)
        }.flowOn(Dispatchers.IO)

    override fun fetchLogin(email: String, password: String): Flow<Boolean> =
        remoteDataSource.fetchLogin(email, password)

    override fun fetchRegister(email: String, password: String): Flow<Boolean> =
        remoteDataSource.fetchRegister(email, password)

    override suspend fun fetchTopRated(page: Int, language: String): Flow<List<MoviesResponse>> =
        flow {
            emit(remoteDataSource.fetchTopRated(page, language).results)
        }.flowOn(Dispatchers.IO)

    override suspend fun fetchUpcoming(page: Int, language: String): Flow<List<MoviesResponse>> =
        flow {
            emit(remoteDataSource.fetchUpcoming(page, language).results)
        }.flowOn(Dispatchers.IO)

    override fun fetchUploadImage(fileStream: FileInputStream, fileName: String): Flow<String> =
        remoteDataSource.fetchUploadImage(fileStream, fileName)

    override fun fetchUploadProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean> =
        remoteDataSource.fetchUploadProfile(userProfileChangeRequest)

    override fun saveLanguage(value: Boolean) = localDataSource.saveLanguage(value)
    override fun saveOnboarding(value: Boolean) = localDataSource.saveOnBoarding(value)
    override fun saveTheme(value: Boolean) = localDataSource.setTheme(value)
    override fun saveUID(value: String) = localDataSource.saveUID(value)
}