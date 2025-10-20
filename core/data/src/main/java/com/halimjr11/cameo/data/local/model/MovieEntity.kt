package com.halimjr11.cameo.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "poster_url")
    val posterUrl: String = "",
    @ColumnInfo(name = "backdrop_url")
    val backdropUrl: String = "",
    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "overview")
    val overview: String = "",
    @ColumnInfo(name = "adult")
    val adult: Boolean = false
)