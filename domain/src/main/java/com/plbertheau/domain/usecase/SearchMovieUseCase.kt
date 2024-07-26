package com.plbertheau.domain.usecase

import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieSearchRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: WookieMovieSearchRepository
) {
    suspend operator fun invoke(query: String): SubmitUiModel<List<MovieResponse>> {
        return repository.searchMovie(query = query)
    }
}