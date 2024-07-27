package com.plbertheau.wookiemovies.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.wookiemovies.R
import com.plbertheau.wookiemovies.ui.screen.error.ErrorItem
import com.plbertheau.wookiemovies.ui.screen.loading.LoadingView
import com.plbertheau.wookiemovies.ui.viewmodel.SearchUiState
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieSearchViewModel
import kotlinx.collections.immutable.PersistentList


@Composable
fun SearchScreen(viewModel: WookieMovieSearchViewModel, onMovieClick: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        searchQuery = viewModel.searchQuery,
        onSearchQueryChange = { viewModel.updateSearchQuery(it) },
        onMovieClick = onMovieClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onMovieClick: (String) -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    ) {
        when {
            uiState.isSearching -> {
                LoadingView()
            }

            uiState.errorMessage != null -> {
                ErrorItem(
                    message = uiState.errorMessage,
                    onClickRetry = {}
                )
            }

            else -> {
                SearchContent(
                    searchQuery = searchQuery,
                    searchResults = uiState.searchResults,
                    queryHasNoResults = uiState.queryHasNoResults,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun SearchContent(
    searchQuery: String,
    searchResults: PersistentList<MovieResponse>,
    queryHasNoResults: Boolean,
    onMovieClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        searchQuery.isEmpty() -> {
            SearchQueryEmptyState()
        }

        queryHasNoResults -> {
            MovieListEmptyState()
        }

        else -> {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                modifier = modifier
            ) {
                items(
                    count = searchResults.size,
                    key = { index -> searchResults[index].id },
                    itemContent = { index ->
                        val movie = searchResults[index]
                        MovieListItem(
                            movie = movie,
                            onMovieClick = onMovieClick
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: MovieResponse,
    onMovieClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }
    ) {
        Text(text = movie.title)
        Text(text = movie.imdb_rating.toString())
    }
}

@Composable
fun MovieListEmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "No movies found",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Try adjusting your search",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SearchQueryEmptyState(modifier: Modifier = Modifier) {
    EmptyState(
        title = stringResource(R.string.empty_state_search_title),
        subtitle = {
            Text(
                text = stringResource(R.string.empty_state_search_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun EmptyState(
    title: String,
    subtitle: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.70f)
                .padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(4.dp))

            subtitle()
        }
    }
}