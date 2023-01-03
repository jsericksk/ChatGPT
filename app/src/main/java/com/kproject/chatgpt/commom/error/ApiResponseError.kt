package com.kproject.chatgpt.commom.error

sealed class ApiResponseError : Exception() {
    object InvalidApiKey : ApiResponseError()
    object MaxTokensReached : ApiResponseError()
    object UnknownError : ApiResponseError()
}