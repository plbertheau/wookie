package com.plbertheau.domain.usecase

import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.repository.WookieMovieSearchRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: WookieMovieSearchRepository
) {
    suspend operator fun invoke(query: String): Result<List<MovieResponse>> {
        return repository.searchMovie(query = query)
    }
}