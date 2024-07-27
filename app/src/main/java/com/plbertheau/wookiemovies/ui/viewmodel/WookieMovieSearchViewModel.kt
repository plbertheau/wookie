package com.plbertheau.wookiemovies.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
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

/**
 * ViewModel for the Wookie Movie Search screen.
 *
 * This ViewModel is responsible for managing the state of the search screen,
 * including the search query, search results, loading state, and error messages.
 *
 * @constructor Creates a new instance of {@codeWookieMovieSearchViewModel}.
 * @param searchMovieUseCase The use case for searching movies.
 */
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

    /**
     * Initializes the UI state and sets up a flow to observe changes in the search query.
     * When the search query changes, it performs a debounced search and updates the UI state
     * accordingly.
     */
    @OptIn(FlowPreview::class)
    fun initialiseUiState() {
        snapshotFlow { searchQuery }
            .debounce(350L)
            .onEach { query ->
                if (query.isNotBlank()) {
                    _uiState.update {
                        it.copy(isSearching = true)
                    }
                    when (val result = searchMovieUseCase(query)) {
                        is Result.Error -> {
                            _uiState.update {
                                it.copy(
                                    errorMessage = result.error,
                                    isSearching = false
                                )
                            }
                        }

                        is Result.Success -> {
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

                        is Result.Loading -> {
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

/**
 * Data class representing the UI state of the search screen.
 *
 * @param searchResults The list of movie search results.
 * @param queryHasNoResults Indicates whether the search query returned no results.
 * @param errorMessage The error message to display, if any.
 * @param isSearching Indicates whether a search is currentlyin progress.
 */
data class SearchUiState(
    val searchResults: PersistentList<MovieResponse> = persistentListOf(),
    val queryHasNoResults: Boolean = false,
    val errorMessage: String? = null,
    val isSearching: Boolean = false
)