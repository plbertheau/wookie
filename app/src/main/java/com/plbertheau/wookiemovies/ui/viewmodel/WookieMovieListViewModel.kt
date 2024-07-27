package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.usecase.GetMovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WookieMovieListViewModel @Inject constructor(
    getMovieListUseCase: GetMovieListUseCase
) : ViewModel() {

    val items: Flow<PagingData<MovieResponse>> = getMovieListUseCase()
        .cachedIn(viewModelScope)

}