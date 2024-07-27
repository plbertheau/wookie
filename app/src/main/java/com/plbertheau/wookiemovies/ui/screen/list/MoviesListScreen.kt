package com.plbertheau.wookiemovies.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.plbertheau.data.model.Genres
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.wookiemovies.ui.screen.error.ErrorItem
import com.plbertheau.wookiemovies.ui.screen.loading.LoadingItem
import com.plbertheau.wookiemovies.ui.screen.loading.LoadingView
import com.plbertheau.wookiemovies.ui.viewmodel.WookieMovieListViewModel


@Composable
fun MovieListScreen(
    viewModel: WookieMovieListViewModel,
    navigateToDetail: (String) -> Unit
) {
    val pagingItems = viewModel.items.collectAsLazyPagingItems()
    ListContent(
        pagingItems = pagingItems,
        navigateToDetail = navigateToDetail,
    )
}

@Composable
fun ListContent(
    pagingItems: LazyPagingItems<MovieResponse>,
    navigateToDetail: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "WOOKIE MOVIES",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start,
            ) {
                Genres.entries.forEach {
                    item {
                        CategoryHeader(text = it.genre)
                    }
                    item {
                        LazyRow {
                            items(
                                count = pagingItems.itemCount,
                            ) { index ->
                                val video = pagingItems[index]
                                if (video != null) {
                                    if (video.genres.contains(it.genre)) {
                                        ItemPortrait(
                                            movie = video,
                                            onClick = {
                                                navigateToDetail(video.id)
                                            },
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .fillMaxHeight()
                                                .padding(8.dp),
                                        )

                                    }
                                }
                            }
                        }
                    }
                    pagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                            }

                            loadState.append is LoadState.Loading -> {
                                item { LoadingItem() }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val e = pagingItems.loadState.refresh as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        modifier = Modifier.fillParentMaxSize(),
                                        onClickRetry = { retry() }
                                    )
                                }
                            }

                            loadState.append is LoadState.Error -> {
                                val e = pagingItems.loadState.append as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = e.error.localizedMessage!!,
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp)
    )
}

@Composable
fun ItemPortrait(
    movie: MovieResponse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .clickable { onClick() }
            .padding(bottom = 8.dp, top = 8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            model = movie.poster,
            modifier = Modifier
                .wrapContentWidth(),
            contentScale = ContentScale.FillHeight,
            contentDescription = null,
        )
    }
}

