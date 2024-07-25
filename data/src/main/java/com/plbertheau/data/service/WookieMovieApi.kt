package com.plbertheau.data.service

import com.plbertheau.domain.model.ResultResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface WookieMovieApi {

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer Wookie2019"
    )
    @GET("/movies")
    suspend fun getMoviesList(
    ): ResultResponse
}