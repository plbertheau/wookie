package com.plbertheau.data.repository

import com.plbertheau.data.service.WookieMovieApi
import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WookieMovieSearchRepositoryImpl @Inject constructor(private val api: WookieMovieApi) :
    WookieMovieSearchRepository {
    override suspend fun searchMovie(query: String): SubmitUiModel<List<MovieResponse>> {
        return try {
            withContext(Dispatchers.Default) {
                val response = api.searchMovie(query)
                val movies = response.movies
                SubmitUiModel.Success(movies)
            }
        } catch (e: Exception) {
            SubmitUiModel.Error("Unable to fetch movie search results")
        }
    }
}
