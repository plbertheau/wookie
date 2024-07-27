package com.plbertheau.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plbertheau.data.Constants.DATABASE_VERSION
import com.plbertheau.data.entity.RemoteKeys
import com.plbertheau.data.model.MovieResponse

@Database(
    entities = [MovieResponse::class, RemoteKeys::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieLocalDB : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}