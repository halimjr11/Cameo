package com.nurhaqhalim.cameo.core.data.remote.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PopularResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<MoviesResponse> = listOf()
) : Parcelable