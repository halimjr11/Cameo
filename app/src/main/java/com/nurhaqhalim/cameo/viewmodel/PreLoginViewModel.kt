package com.nurhaqhalim.cameo.viewmodel

import androidx.lifecycle.ViewModel
import com.nurhaqhalim.cameo.core.domain.model.DataSession
import com.nurhaqhalim.cameo.core.domain.state.FlowState
import com.nurhaqhalim.cameo.core.domain.state.SplashState
import com.nurhaqhalim.cameo.core.domain.usecase.CameoUseCase
import com.nurhaqhalim.cameo.core.utils.DataMapper.toSplashState
import com.nurhaqhalim.cameo.core.utils.validateEmail
import com.nurhaqhalim.cameo.core.utils.validatePassword
import com.nurhaqhalim.cameo.core.utils.validateRequired
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.FileInputStream

class PreLoginViewModel(private val cameoUseCase: CameoUseCase) : ViewModel() {
    private val _onboarding: MutableStateFlow<SplashState<DataSession>> =
        MutableStateFlow(SplashState.OnBoarding)
    val onboarding = _onboarding.asStateFlow()
    private val _validateRegisterEmail: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateRegisterEmail = _validateRegisterEmail.asStateFlow()
    private val _validateRegisterPassword: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateRegisterPassword = _validateRegisterPassword.asStateFlow()
    private val _validateLoginEmail: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateLoginEmail = _validateLoginEmail.asStateFlow()
    private val _validateLoginPassword: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateLoginPassword = _validateLoginPassword.asStateFlow()
    private val _validateProfileName: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateProfileName = _validateProfileName.asStateFlow()
    private val _validateLoginField: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateLoginField = _validateLoginField.asStateFlow()
    private val _validateRegisterField: MutableStateFlow<FlowState<Boolean>> =
        MutableStateFlow(FlowState.FlowCreated)
    val validateRegisterField = _validateRegisterField.asSharedFlow()


    fun fetchLogin(email: String, password: String): Flow<Boolean> =
        cameoUseCase.fetchLogin(email, password)

    fun fetchRegister(email: String, password: String): Flow<Boolean> =
        cameoUseCase.fetchRegister(email, password)

    fun fetchUploadImage(fileStream: FileInputStream, fileName: String): Flow<String> =
        cameoUseCase.fetchUploadImage(fileStream, fileName)

    fun fetchUploadProfile(name: String, imageUrl: String): Flow<Boolean> =
        cameoUseCase.fetchUploadProfile(name, imageUrl)

    fun getOnboardingState() {
        _onboarding.update {
            cameoUseCase.getSessionData().toSplashState()
        }
    }

    fun saveProfile() = cameoUseCase.saveSession()

    fun setOnBoarding(value: Boolean) {
        cameoUseCase.saveOnboarding(value)
    }

    fun validateRegisterEmail(email: String) {
        _validateRegisterEmail.update { FlowState.FlowValue(email.validateEmail()) }
    }

    fun validateRegisterPassword(password: String) {
        _validateRegisterPassword.update { FlowState.FlowValue(password.validatePassword()) }
    }

    fun validateLoginEmail(email: String) {
        _validateLoginEmail.update { FlowState.FlowValue(email.validateEmail()) }
    }

    fun validateLoginPassword(password: String) {
        _validateLoginPassword.update { FlowState.FlowValue(password.validatePassword()) }
    }

    fun validateLoginField(email: String, password: String) {
        _validateLoginField.update { FlowState.FlowValue(email.validateRequired() && password.validateRequired()) }
    }

    fun resetLoginField() {
        _validateLoginField.update { FlowState.FlowCreated }
    }

    fun validateRegisterField(email: String, password: String) {
        _validateRegisterField.update { FlowState.FlowValue(email.validateRequired() && password.validateRequired()) }
    }

    fun resetRegisterField() {
        _validateRegisterField.update { FlowState.FlowCreated }
    }

    fun validateProfileName(profileName: String) {
        _validateProfileName.update { FlowState.FlowValue(profileName.validateRequired()) }
    }
}