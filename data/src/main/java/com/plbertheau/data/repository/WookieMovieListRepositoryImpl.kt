package com.plbertheau.data.repository

import com.plbertheau.data.service.WookieMovieApi
import com.plbertheau.domain.repository.WookieMovieListRepository
import javax.inject.Inject

class WookieMovieListRepositoryImpl @Inject constructor(private val wookieMovieApi: WookieMovieApi): WookieMovieListRepository{
}