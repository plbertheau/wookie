package com.plbertheau.data.repository

import com.plbertheau.data.common.Result
import com.plbertheau.data.model.MovieResponse
import com.plbertheau.data.service.WookieMovieApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WookieMovieSearchRepositoryImpl @Inject constructor(private val api: WookieMovieApi) :
    WookieMovieSearchRepository {
    override suspend fun searchMovie(query: String): Result<List<MovieResponse>> {
        return try {
            withContext(Dispatchers.Default) {
                val response = api.searchMovie(query)
                val movies = response.movies
                Result.Success(movies)
            }
        } catch (e: Exception) {
            Result.Error("Unable to fetch movie search results")
        }
    }
}
