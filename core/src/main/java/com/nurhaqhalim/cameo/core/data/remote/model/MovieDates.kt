package com.nurhaqhalim.cameo.core.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MovieDates(
    @SerializedName("maximum")
    val maximum: String = "",
    @SerializedName("minimum")
    val minimum: String = ""
) : Parcelable
