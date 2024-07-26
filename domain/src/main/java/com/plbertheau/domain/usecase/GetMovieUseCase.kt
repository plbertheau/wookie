package com.plbertheau.domain.usecase

import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: WookieMovieRepository
) {
    operator fun invoke(query: String): Flow<SubmitUiModel<MovieResponse>> {
        return repository.getMovie(query).flowOn(Dispatchers.Default)
    }
}