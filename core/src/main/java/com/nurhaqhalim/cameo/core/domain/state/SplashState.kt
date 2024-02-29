package com.nurhaqhalim.cameo.core.domain.state

sealed class SplashState<out R> {
    data object Login : SplashState<Nothing>()
    data object Main : SplashState<Nothing>()
    data object OnBoarding : SplashState<Nothing>()
    data object Profile : SplashState<Nothing>()
}

fun <T> SplashState<T>.doOnLogin(
    execute: () -> Unit
): SplashState<T> = apply {
    if (this is SplashState.Login) {
        execute()
    }
}

fun <T> SplashState<T>.doOnMain(
    execute: () -> Unit
): SplashState<T> = apply {
    if (this is SplashState.Main) {
        execute()
    }
}

fun <T> SplashState<T>.doOnBoarding(
    execute: () -> Unit
): SplashState<T> = apply {
    if (this is SplashState.OnBoarding) {
        execute()
    }
}

fun <T> SplashState<T>.doOnProfile(
    execute: () -> Unit
): SplashState<T> = apply {
    if (this is SplashState.Profile) {
        execute()
    }
}
