package com.nurhaqhalim.cameo.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DataMovie(
    val backdropPath: String = "",
    val id: Int = 0,
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val releaseDate: String = "",
    val title: String = ""
) : Parcelable
