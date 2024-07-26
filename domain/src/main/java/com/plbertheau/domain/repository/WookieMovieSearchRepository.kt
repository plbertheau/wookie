package com.plbertheau.domain.repository

import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse

interface WookieMovieSearchRepository {
    suspend fun searchMovie(query: String): SubmitUiModel<List<MovieResponse>>
}