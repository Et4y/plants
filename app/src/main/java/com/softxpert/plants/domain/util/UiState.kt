package com.softxpert.plants.domain.util

sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error<T>(val throwable: Throwable) : UiState<T>()
    data object Loading : UiState<Nothing>()
    data object Idle : UiState<Nothing>()
}