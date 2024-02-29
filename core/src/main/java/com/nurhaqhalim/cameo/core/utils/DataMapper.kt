package com.nurhaqhalim.cameo.core.utils

import com.google.firebase.auth.FirebaseUser
import com.nurhaqhalim.cameo.core.data.remote.model.MoviesResponse
import com.nurhaqhalim.cameo.core.domain.model.DataMovie
import com.nurhaqhalim.cameo.core.domain.model.DataSession
import com.nurhaqhalim.cameo.core.domain.model.UserModel
import com.nurhaqhalim.cameo.core.domain.state.SplashState

object DataMapper {
    fun FirebaseUser?.toUiData(): UserModel? = this?.let { user ->
        displayName?.let { dn ->
            UserModel(uid = uid, name = dn)
        }
    }

    fun Triple<String, String, Boolean>.toUiData() = DataSession(
        displayName = this.first,
        uid = this.second,
        onBoardingState = this.third
    )

    fun DataSession.toSplashState() = when {
        this.displayName.isNotEmpty() && this.uid.isNotEmpty() -> {
            SplashState.Main
        }

        this.displayName.isEmpty() && this.uid.isNotEmpty() -> {
            SplashState.Profile
        }

        this.onBoardingState -> {
            SplashState.Login
        }

        else -> {
            SplashState.OnBoarding
        }
    }

    fun MoviesResponse.toUiData() = DataMovie(
        backdropPath = backdropPath,
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        releaseDate = releaseDate,
        title = title
    )


}