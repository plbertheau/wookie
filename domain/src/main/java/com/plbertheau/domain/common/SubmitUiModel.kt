package com.plbertheau.domain.common

sealed class  SubmitUiModel<T> {

    abstract val data: T?

    data class Loading<T>(override val data: T? = null) : SubmitUiModel<T>()
    data class Success<T>(override val data: T?) : SubmitUiModel<T>()
    data class Error<T>(val error: String, override val data: T? = null) : SubmitUiModel<T>()
}