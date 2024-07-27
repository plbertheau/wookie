package com.plbertheau.data.repository

import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface WookieMovieRepository {
    fun getMovie(id: String): Flow<Result<MovieResponse>>
}