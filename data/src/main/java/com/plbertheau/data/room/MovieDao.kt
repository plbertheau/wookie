package com.plbertheau.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.plbertheau.domain.model.MovieResponse

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<MovieResponse>)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM movie")
    fun getAll(): PagingSource<Int, MovieResponse>

    @Query("DELETE FROM movie")
    suspend fun clear()

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getById(id: String): MovieResponse?
}

