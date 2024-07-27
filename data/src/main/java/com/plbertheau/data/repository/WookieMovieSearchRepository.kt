package com.plbertheau.data.repository

import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse

interface WookieMovieSearchRepository {
    suspend fun searchMovie(query: String): Result<List<MovieResponse>>
}