package com.halimjr11.cameo.data.network.model


import com.google.gson.annotations.SerializedName

data class MovieCreditResponse(
    @SerializedName("cast")
    val cast: List<Cast?>? = null,
    @SerializedName("crew")
    val crew: List<Crew?>? = null,
    @SerializedName("id")
    val id: Int? = null
) {
    data class Cast(
        @SerializedName("adult")
        val adult: Boolean? = null,
        @SerializedName("cast_id")
        val castId: Int? = null,
        @SerializedName("character")
        val character: String? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        @SerializedName("gender")
        val gender: Int? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("known_for_department")
        val knownForDepartment: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("order")
        val order: Int? = null,
        @SerializedName("original_name")
        val originalName: String? = null,
        @SerializedName("popularity")
        val popularity: Double? = null,
        @SerializedName("profile_path")
        val profilePath: String? = null
    )

    data class Crew(
        @SerializedName("adult")
        val adult: Boolean? = null,
        @SerializedName("credit_id")
        val creditId: String? = null,
        @SerializedName("department")
        val department: String? = null,
        @SerializedName("gender")
        val gender: Int? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("job")
        val job: String? = null,
        @SerializedName("known_for_department")
        val knownForDepartment: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("original_name")
        val originalName: String? = null,
        @SerializedName("popularity")
        val popularity: Double? = null,
        @SerializedName("profile_path")
        val profilePath: String? = null
    )
}