package com.nurhaqhalim.cameo.core.data.remote.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UpcomingResponse(
    @SerializedName("dates")
    val dates: MovieDates = MovieDates(),
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<MoviesResponse> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("total_results")
    val totalResults: Int = 0
) : Parcelable