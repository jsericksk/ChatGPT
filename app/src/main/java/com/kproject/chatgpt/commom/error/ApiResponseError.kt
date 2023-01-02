package com.kproject.chatgpt.commom.error

sealed class ApiResponseError : Exception() {
    object MaxTokensReached : ApiResponseError()
    object InvalidApiKey : ApiResponseError()
    object UnknownError : ApiResponseError()
}