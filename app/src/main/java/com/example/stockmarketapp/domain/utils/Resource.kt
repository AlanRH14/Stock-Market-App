package com.example.stockmarketapp.domain.utils

sealed interface Resource<out T> {
    data class Success<T>(val data: T?) : Resource<T>
    data class Error<T>(val data: T?, val message: String) : Resource<T>
    data object Loading : Resource<Nothing>
}