package ru.akella.cryptocoin.domain

sealed class Result<V> {

    data class Loading<V>(val data: V? = null) : Result<V>()
    data class Success<V>(val data: V) : Result<V>()
    data class Error<V>(val e: Throwable) : Result<V>()
}

fun <T: Any?> T.asSuccess(): Result<T> = Result.Success(this)

fun <T: Any?> T.asLoading(): Result<T> = Result.Loading(this)

fun <V> Result<V>.fold(onSuccess: (V?) -> Unit, onError: (Throwable) -> Unit) {
    when (this) {
        is Result.Success<V> -> onSuccess(this.data)
        is Result.Error<V> -> onError(this.e)
        else -> { /* do nothing */ }
    }
}