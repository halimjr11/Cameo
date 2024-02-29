package com.nurhaqhalim.cameo.core.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.nurhaqhalim.cameo.core.data.remote.model.MoviesResponse
import com.nurhaqhalim.cameo.core.data.remote.model.NowPlayingResponse
import com.nurhaqhalim.cameo.core.data.remote.model.PopularResponse
import com.nurhaqhalim.cameo.core.data.remote.model.TopRatedResponse
import com.nurhaqhalim.cameo.core.data.remote.model.UpcomingResponse
import kotlinx.coroutines.flow.Flow
import java.io.FileInputStream

interface CameoRepository {
    fun clearSessionData()
    fun getCurrentUser(): FirebaseUser?
    fun fetchLogin(email: String, password: String): Flow<Boolean>
    fun fetchLogout()
    suspend fun fetchNowPlaying(page: Int, language: String): Flow<List<MoviesResponse>>
    suspend fun fetchPopular(page: Int, language: String): Flow<List<MoviesResponse>>
    fun fetchRegister(email: String, password: String): Flow<Boolean>
    suspend fun fetchTopRated(page: Int, language: String): Flow<List<MoviesResponse>>
    suspend fun fetchUpcoming(page: Int, language: String): Flow<List<MoviesResponse>>
    fun fetchUploadImage(fileStream: FileInputStream, fileName: String): Flow<String>
    fun fetchUploadProfile(userProfileChangeRequest: UserProfileChangeRequest): Flow<Boolean>
    fun getLanguage(): Boolean
    fun getOnboardingState(): Boolean
    fun getTheme(): Boolean
    fun getUID(): String
    fun saveLanguage(value: Boolean)
    fun saveOnboarding(value: Boolean)
    fun saveTheme(value: Boolean)
    fun saveUID(value: String)
}