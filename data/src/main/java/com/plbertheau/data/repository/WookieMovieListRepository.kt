package com.plbertheau.data.repository

import androidx.paging.PagingData
import com.plbertheau.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface WookieMovieListRepository {
    fun wookieMoviesPagingSource(): Flow<PagingData<MovieResponse>>
}