package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.usecase.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WookieMovieSearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    var searchQuery by mutableStateOf("")
        private set

    init {
        initialiseUiState()
    }

    @OptIn(FlowPreview::class)
    private fun initialiseUiState() {
        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if (query.isNotBlank()) {
                    _uiState.update {
                        it.copy(isSearching = true)
                    }
                    when (val result = searchMovieUseCase(query)) {
                        is SubmitUiModel.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.error,
                                    isSearching = false
                                )
                            }
                        }

                        is SubmitUiModel.Success -> {
                            val searchResults = result.data?.toPersistentList()

                            if (searchResults != null) {
                                _uiState.update {
                                    it.copy(
                                        searchResults = searchResults,
                                        queryHasNoResults = searchResults.isEmpty(),
                                        isSearching = false,
                                        errorMessage = null
                                    )
                                }
                            }
                        }

                        is SubmitUiModel.Loading -> {
                            _uiState.update {
                                it.copy(
                                    searchResults = persistentListOf(),
                                    errorMessage = null,
                                    isSearching = true
                                )
                            }
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            searchResults = persistentListOf(),
                            queryHasNoResults = false,
                            isSearching = false,
                            errorMessage = null
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun updateSearchQuery(newQuery: String) {
        searchQuery = newQuery
    }
}

data class SearchUiState(
    val searchResults: PersistentList<MovieResponse> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val errorMessage: String? = null,
    val isSearching: Boolean = false
)