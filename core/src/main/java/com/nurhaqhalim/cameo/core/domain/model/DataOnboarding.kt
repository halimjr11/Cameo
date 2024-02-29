package com.nurhaqhalim.cameo.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataOnboarding(
    val text: String = "",
    val image: Int = 0
) : Parcelable