package com.plbertheau.data.repository

import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.room.MovieLocalDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WookieMovieRepositoryImpl @Inject constructor(private val movieLocalDB: MovieLocalDB) :
    WookieMovieRepository {
    override fun getMovie(id: String): Flow<Result<MovieResponse>> = flow {
        val movieResponse = movieLocalDB.getMovieDao().getById(id)
        if (movieResponse != null) emit(Result.Success(movieResponse))
    }
}