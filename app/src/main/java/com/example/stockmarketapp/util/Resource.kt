package com.example.stockmarketapp.util

sealed class Resource<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(data: T?, message: String) : Resource<T>(data = data, message = message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(data = null, message = null)
}