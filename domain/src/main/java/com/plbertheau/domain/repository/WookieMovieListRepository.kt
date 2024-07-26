package com.plbertheau.domain.repository

import androidx.paging.PagingData
import com.plbertheau.domain.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface WookieMovieListRepository {
    fun wookieMoviesPagingSource(): Flow<PagingData<MovieResponse>>
}