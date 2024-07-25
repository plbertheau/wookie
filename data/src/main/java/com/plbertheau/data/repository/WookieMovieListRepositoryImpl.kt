package com.plbertheau.data.repository

import androidx.paging.PagingSource
import com.plbertheau.data.service.WookieMovieApi
import com.plbertheau.data.service.WookieMoviesListPagingSource
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieListRepository
import javax.inject.Inject

class WookieMovieListRepositoryImpl @Inject constructor(private val wookieMovieApi: WookieMovieApi) :
    WookieMovieListRepository {
    override fun wookieMoviesPagingSource(): PagingSource<Int, MovieResponse> =
        WookieMoviesListPagingSource(wookieMovieApi)
}