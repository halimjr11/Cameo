package com.nurhaqhalim.cameo.core.domain.usecase

import com.nurhaqhalim.cameo.core.data.remote.model.MoviesResponse
import com.nurhaqhalim.cameo.core.domain.model.DataSession
import com.nurhaqhalim.cameo.core.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import java.io.FileInputStream

interface CameoUseCase {
    fun clearSessionData()
    fun getCurrentUser(): UserModel?
    fun getLanguage(): Boolean
    fun getOnboardingState(): Boolean
    fun getSessionData(): DataSession
    fun getTheme(): Boolean
    fun getUID(): String
    fun fetchLogin(email: String, password: String): Flow<Boolean>
    fun fetchLogout()
    suspend fun fetchNowPlaying(page: Int, language: String): Flow<List<MoviesResponse>>
    suspend fun fetchPopular(page: Int, language: String): Flow<List<MoviesResponse>>
    fun fetchRegister(email: String, password: String): Flow<Boolean>
    suspend fun fetchTopRated(page: Int, language: String): Flow<List<MoviesResponse>>
    suspend fun fetchUpcoming(page: Int, language: String): Flow<List<MoviesResponse>>
    fun fetchUploadImage(fileStream: FileInputStream, fileName: String): Flow<String>
    fun fetchUploadProfile(name: String, imageUrl: String): Flow<Boolean>
    fun saveLanguage(value: Boolean)
    fun saveOnboarding(value: Boolean)
    fun saveSession()
    fun saveTheme(value: Boolean)
    fun saveUID(value: String)
}