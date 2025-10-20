package com.halimjr11.cameo.domain.model

data class MovieDomain(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val backdropUrl: String = "",
    val rating: Double = 0.0,
    val releaseDate: String = "",
    val language: String = "",
    val overview: String = "",
    val genreIds: List<Int> = emptyList(),
    val adult: Boolean = false
)