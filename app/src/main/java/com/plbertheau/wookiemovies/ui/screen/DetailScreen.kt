package com.plbertheau.wookiemovies.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieDetailViewModel


@Composable
fun DetailScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: WookieMovieDetailViewModel = hiltViewModel()
) {
    val movieResponse by viewModel.movieResponse.collectAsStateWithLifecycle()

    if (movieResponse is SubmitUiModel.Error<*>) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar((movieResponse as SubmitUiModel.Error<*>).error)
        }
    }

    UserDetailContent(movieResponse = movieResponse)
}

@Composable
fun UserDetailContent(movieResponse: SubmitUiModel<MovieResponse>) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (movieResponse.data != null) {
            val movie = movieResponse.data!!
            AsyncImage(
                model = movie.backdrop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    movie.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        if (movieResponse is SubmitUiModel.Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}