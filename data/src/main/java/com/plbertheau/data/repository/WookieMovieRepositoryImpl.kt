package com.plbertheau.data.repository

import com.plbertheau.data.room.MovieLocalDB
import com.plbertheau.domain.common.SubmitUiModel
import com.plbertheau.domain.model.MovieResponse
import com.plbertheau.domain.repository.WookieMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WookieMovieRepositoryImpl @Inject constructor(private val movieLocalDB: MovieLocalDB) :
    WookieMovieRepository {
    override fun getMovie(id: String): Flow<SubmitUiModel<MovieResponse>> = flow {
        val movieResponse = movieLocalDB.getMovieDao().getById(id)
        if (movieResponse != null) emit(SubmitUiModel.Success(movieResponse))
    }


}