package com.plbertheau.data.common

sealed class Result<T> {

    abstract val data: T?

    data class Loading<T>(override val data: T? = null) : Result<T>()
    data class Success<T>(override val data: T?) : Result<T>()
    data class Error<T>(val error: String, override val data: T? = null) : Result<T>()
}