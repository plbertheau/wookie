package com.plbertheau.domain.usecase

import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.repository.WookieMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: WookieMovieRepository
) {
    operator fun invoke(query: String): Flow<Result<MovieResponse>> {
        return repository.getMovie(query).flowOn(Dispatchers.Default)
    }
}