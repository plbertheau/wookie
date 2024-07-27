package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.domain.usecase.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ViewModel for the Wookie Movie List screen.
 *
 * This ViewModel is responsible for fetching and providing a paginated list of
 * {@link MovieResponse} objects to the UI.
 *
 * @constructor Creates a new instance of {@code WookieMovieListViewModel}.
 * @param getMovieListUseCase The use case for retrieving the movie list.
 */
@HiltViewModel
class WookieMovieListViewModel @Inject constructor(
    getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    val items: Flow<PagingData<MovieResponse>> = getMovieListUseCase()
        .cachedIn(viewModelScope)
}