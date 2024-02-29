package com.nurhaqhalim.cameo.core.data.local

import com.nurhaqhalim.cameo.core.data.local.sharedpreference.SharedPreferenceHelper

class LocalDataSource(
    private val sharedPreferenceHelper: SharedPreferenceHelper,
//    private val cameoDao: CameoDao
) {
    fun getLanguageState(): Boolean = sharedPreferenceHelper.retrieveLanguageState()
    fun getOnboardingState(): Boolean = sharedPreferenceHelper.retrieveOnBoardingState()
    fun getThemeState(): Boolean = sharedPreferenceHelper.retrieveThemeState()
    fun getUID(): String = sharedPreferenceHelper.retrieveUID().orEmpty()
    fun saveLanguage(value: Boolean) = sharedPreferenceHelper.saveLanguageState(value)
    fun saveOnBoarding(value: Boolean) = sharedPreferenceHelper.saveOnBoardingState(value)
    fun setTheme(value: Boolean) = sharedPreferenceHelper.saveThemeState(value)
    fun saveUID(value: String) = sharedPreferenceHelper.saveUID(value)
    fun clearSessionData() = sharedPreferenceHelper.clearSessionData()
}