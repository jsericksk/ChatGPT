package com.kproject.chatgpt.commom

sealed class DataState<out T>(val result: T? = null) {
    data class Success<T>(val data: T? = null) : DataState<T>(result = data)
    data class Error<T>(val exception: Exception? = null) : DataState<T>()
}