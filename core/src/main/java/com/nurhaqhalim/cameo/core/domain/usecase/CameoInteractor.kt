package com.nurhaqhalim.cameo.core.domain.usecase

import com.google.firebase.auth.userProfileChangeRequest
import com.nurhaqhalim.cameo.core.data.CameoRepository
import com.nurhaqhalim.cameo.core.data.remote.model.MoviesResponse
import com.nurhaqhalim.cameo.core.domain.model.DataSession
import com.nurhaqhalim.cameo.core.domain.model.UserModel
import com.nurhaqhalim.cameo.core.utils.DataMapper.toUiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import java.io.FileInputStream

class CameoInteractor(private val cameoRepository: CameoRepository) : CameoUseCase {
    override fun clearSessionData() = cameoRepository.clearSessionData()
    override fun getCurrentUser(): UserModel? = cameoRepository.getCurrentUser().toUiData()
    override fun getLanguage(): Boolean = cameoRepository.getLanguage()
    override fun getOnboardingState(): Boolean = cameoRepository.getOnboardingState()
    override fun getSessionData(): DataSession {
        val name = getCurrentUser()?.name.orEmpty()
        val uid = getUID()
        val onBoardingState = getOnboardingState()
        println("Upin : $name, $uid, $onBoardingState")
        val triple: Triple<String, String, Boolean> = Triple(name, uid, onBoardingState)
        return triple.toUiData()
    }

    override fun getTheme(): Boolean = cameoRepository.getTheme()
    override fun getUID(): String = cameoRepository.getUID()
    override fun fetchLogout() = cameoRepository.fetchLogout()
    override suspend fun fetchNowPlaying(page: Int, language: String): Flow<List<MoviesResponse>> =
        cameoRepository.fetchNowPlaying(page, language).transform { list ->
            list.map { movie -> movie.toUiData() }
        }

    override suspend fun fetchPopular(page: Int, language: String): Flow<List<MoviesResponse>> =
        cameoRepository.fetchPopular(page, language).transform { list ->
            list.map { movie -> movie.toUiData() }
        }

    override fun fetchLogin(email: String, password: String): Flow<Boolean> =
        cameoRepository.fetchLogin(email, password)

    override fun fetchRegister(email: String, password: String): Flow<Boolean> =
        cameoRepository.fetchRegister(email, password)

    override suspend fun fetchTopRated(page: Int, language: String): Flow<List<MoviesResponse>> =
        cameoRepository.fetchNowPlaying(page, language).transform { list ->
            list.map { movie -> movie.toUiData() }
        }

    override suspend fun fetchUpcoming(page: Int, language: String): Flow<List<MoviesResponse>> =
        cameoRepository.fetchNowPlaying(page, language).transform { list ->
            list.map { movie -> movie.toUiData() }
        }

    override fun fetchUploadImage(fileStream: FileInputStream, fileName: String): Flow<String> =
        cameoRepository.fetchUploadImage(fileStream, fileName)

    override fun fetchUploadProfile(name: String, imageUrl: String): Flow<Boolean> {
        val userProfileChangeRequest = userProfileChangeRequest {
            displayName = name

        }
        return cameoRepository.fetchUploadProfile(userProfileChangeRequest)
    }

    override fun saveLanguage(value: Boolean) = cameoRepository.saveLanguage(value)
    override fun saveOnboarding(value: Boolean) = cameoRepository.saveOnboarding(value)
    override fun saveSession() {
        getCurrentUser()?.let {
            saveUID(it.uid)
        }
    }

    override fun saveTheme(value: Boolean) = cameoRepository.saveTheme(value)
    override fun saveUID(value: String) = cameoRepository.saveUID(value)
}