package com.plbertheau.domain.model

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @field:SerializedName("movies") val movies: List<MovieResponse> = emptyList(),
    @field:SerializedName("page") val page: Int?
)