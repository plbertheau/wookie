package com.plbertheau.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.plbertheau.data.service.WookieMovieApi
import com.plbertheau.data.service.WookieMoviesListPagingSource
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WookieMovieListRepositoryImpl @Inject constructor(private val moviePager: Pager<Int, MovieResponse>,) :
    WookieMovieListRepository {
    override fun wookieMoviesPagingSource(): Flow<PagingData<MovieResponse>> {
        return moviePager.flow.map { pagingData ->
            pagingData.map { it }
        }
    }
}