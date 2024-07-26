package com.plbertheau.domain.repository

import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface WookieMovieRepository {
    fun getMovie(id: String): Flow<SubmitUiModel<MovieResponse>>
}