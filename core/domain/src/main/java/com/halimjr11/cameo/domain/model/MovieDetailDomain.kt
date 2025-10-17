package com.halimjr11.cameo.domain.model

data class MovieDetailDomain(
    val id: Int = 0,
    val title: String = "",
    val genres: List<String> = emptyList(),
    val overview: String = "",
    val rating: Double = 0.0,
    val posterUrl: String = "",
    val backdropUrl: String = "",
    val runtime: String = "",
    val releaseDate: String = "",
    val spokenLanguages: List<String> = emptyList(),
    val cast: List<CastDomain> = emptyList()
) {
    data class CastDomain(
        val id: Int = 0,
        val name: String = "",
        val profileUrl: String = "",
        val character: String = ""
    )
}