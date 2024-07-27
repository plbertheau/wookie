package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.domain.usecase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class WookieMovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieUseCase: GetMovieUseCase
) : ViewModel() {
    private val argument = checkNotNull(savedStateHandle.get<String>("id"))

    val movieResponse: StateFlow<Result<MovieResponse>> =
        getMovieUseCase(argument).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading(),
        )

}