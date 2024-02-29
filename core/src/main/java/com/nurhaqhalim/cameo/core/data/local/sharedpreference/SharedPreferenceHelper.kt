package com.nurhaqhalim.cameo.core.data.local.sharedpreference

interface SharedPreferenceHelper {
    fun saveOnBoardingState(value: Boolean)
    fun retrieveOnBoardingState(): Boolean
    fun saveUID(value: String)
    fun retrieveUID(): String?
    fun saveThemeState(value: Boolean)
    fun retrieveThemeState(): Boolean
    fun saveLanguageState(value: Boolean)
    fun retrieveLanguageState(): Boolean
    fun clearSessionData()
}