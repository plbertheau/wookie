package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plbertheau.data.Constants
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WookieMovieListViewModel @Inject constructor(
    repository: WookieMovieListRepository
) : ViewModel() {

    val items: Flow<PagingData<MovieResponse>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { repository.wookieMoviesPagingSource() }
        ).flow.cachedIn(viewModelScope)

}