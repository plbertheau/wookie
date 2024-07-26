package com.plbertheau.domain.usecase

import androidx.paging.PagingData
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val wookieMovieListRepository: WookieMovieListRepository) {
    operator fun invoke(): Flow<PagingData<MovieResponse>> =
        wookieMovieListRepository.wookieMoviesPagingSource().flowOn(Dispatchers.IO)
}