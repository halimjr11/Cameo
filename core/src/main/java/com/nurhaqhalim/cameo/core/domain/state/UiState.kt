package com.nurhaqhalim.cameo.core.domain.state

sealed class UiState<out R> {
    data object Empty : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Error(val error: Throwable) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
}

fun <T> UiState<T>.onSuccess(
    execute: (data: T) -> Unit
): UiState<T> = apply {
    if (this is UiState.Success) {
        println("TagUIState" + data.toString())
        execute(data)
    }
}

fun <T> UiState<T>.onError(
    execute: (error: Throwable) -> Unit
): UiState<T> = apply {
    if (this is UiState.Error) {
        println("TagUIState" + error.message)
        execute(error)
    }
}

fun <T> UiState<T>.onLoading(
    execute: () -> Unit
): UiState<T> = apply {
    if (this is UiState.Loading) {
        println("TagUIState Loading")
        execute()
    }
}
