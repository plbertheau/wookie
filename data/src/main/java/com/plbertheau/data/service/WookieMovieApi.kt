package com.plbertheau.data.service

import com.plbertheau.domain.model.ResultResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WookieMovieApi {

    @Headers(
        "Authorization: Bearer Wookie2019"
    )
    @GET("/movies")
    suspend fun getMoviesList(): ResultResponse

    @Headers(
        "Authorization: Bearer Wookie2019"
    )
    @GET("/movies")
    suspend fun searchMovie(@Query("q") search_term: String): ResultResponse
}