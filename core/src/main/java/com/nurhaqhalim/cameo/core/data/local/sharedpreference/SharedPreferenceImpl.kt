package com.nurhaqhalim.cameo.core.data.local.sharedpreference

import android.content.SharedPreferences

class SharedPreferenceImpl(private val sharedPreferences: SharedPreferences) :
    SharedPreferenceHelper {
    override fun saveOnBoardingState(value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(onboardingKey, value)
            apply()
        }
    }

    override fun retrieveOnBoardingState(): Boolean =
        sharedPreferences.getBoolean(onboardingKey, false)

    override fun saveUID(value: String) {
        sharedPreferences.edit().apply {
            putString(uidKey, value)
            apply()
        }
    }

    override fun retrieveUID(): String? = sharedPreferences.getString(uidKey, "")

    override fun saveThemeState(value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(themeKey, value)
            apply()
        }
    }

    override fun retrieveThemeState(): Boolean = sharedPreferences.getBoolean(themeKey, false)

    override fun saveLanguageState(value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(languageKey, value)
            apply()
        }
    }

    override fun retrieveLanguageState(): Boolean =
        sharedPreferences.getBoolean(languageKey, false)

    override fun clearSessionData() {
        sharedPreferences.edit().apply {
            remove(onboardingKey)
            remove(languageKey)
            remove(themeKey)
            remove(uidKey)
            apply()
        }

    }

    private companion object {
        const val onboardingKey = "onboarding_passed"
        const val uidKey = "user_id_key"
        const val themeKey = "theme_key"
        const val languageKey = "language_key"
    }
}