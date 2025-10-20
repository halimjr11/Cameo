package com.halimjr11.cameo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.halimjr11.cameo.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}