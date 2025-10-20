package com.halimjr11.cameo.view.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.common.helper.LanguageManager
import com.halimjr11.cameo.common.helper.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeManager: ThemeManager,
    private val languageManager: LanguageManager,
    private val dispatcher: CoroutineDispatcherProvider
) : ViewModel() {
    private val _isDarkModeEnabled = MutableStateFlow(themeManager.isSystemDarkMode())
    val isDarkModeEnabled: StateFlow<Boolean> = _isDarkModeEnabled

    private val _currentLanguage = MutableStateFlow(languageManager.getCurrentLanguage())
    val currentLanguage: StateFlow<String> = _currentLanguage


    fun toggleDarkMode(enabled: Boolean) = viewModelScope.launch {
        _isDarkModeEnabled.value = enabled
        themeManager.applyTheme(enabled)
    }

    fun toggleLanguage(language: String) = viewModelScope.launch {
        _currentLanguage.value = language
        languageManager.updateResources(language)
    }

}