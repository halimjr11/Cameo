package com.nurhaqhalim.cameo.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataSession(
    var displayName: String = "",
    var uid: String = "",
    var onBoardingState: Boolean = false
) : Parcelable

