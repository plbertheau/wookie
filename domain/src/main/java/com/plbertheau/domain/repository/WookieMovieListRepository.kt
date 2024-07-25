package com.plbertheau.domain.repository

import androidx.paging.PagingSource
import com.plbertheau.domain.model.MovieResponse

interface WookieMovieListRepository {
    fun wookieMoviesPagingSource(): PagingSource<Int, MovieResponse>
}