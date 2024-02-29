package com.nurhaqhalim.cameo.viewmodel

import androidx.lifecycle.ViewModel
import com.nurhaqhalim.cameo.core.domain.usecase.CameoUseCase

class MainViewModel(private val cameoUseCase: CameoUseCase) : ViewModel() {
    fun getDisplayName(): String = cameoUseCase.getCurrentUser()?.name.orEmpty()
}