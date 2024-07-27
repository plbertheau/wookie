package com.plbertheau.wookiemovies.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieDetailViewModel
import com.plbertheau.wookiemovies.utils.getYear


@Composable
fun DetailScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: WookieMovieDetailViewModel
) {
    val movieResponse by viewModel.movieResponse.collectAsStateWithLifecycle()

    if (movieResponse is Result.Error<*>) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar((movieResponse as Result.Error<*>).error)
        }
    }

    UserDetailContent(movieResponse = movieResponse)
}

@Composable
fun UserDetailContent(movieResponse: Result<MovieResponse>) {
    if (movieResponse.data != null) {
        val movie = movieResponse.data!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Start)
                    .background(color = MaterialTheme.colorScheme.background)
                    .height(320.dp)
            ) {
                AsyncImage(
                    model = movie.backdrop,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                AsyncImage(
                    model = movie.poster,
                    modifier = Modifier
                        .padding(top = 100.dp, start = 24.dp)
                        .aspectRatio(0.6f),
                    contentScale = ContentScale.Inside,
                    contentDescription = null,
                )
            }
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 24.dp, end = 24.dp),
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                text = getYear(movie.released_on),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                text = movie.length,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                text = movie.cast.toString().replace("[","").replace("]",""),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp),
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }


        if (movieResponse is Result.Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}