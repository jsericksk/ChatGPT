package com.kproject.chatgpt.data.api.entity

data class ErrorResponse(
    val error: Error
)

data class Error(
    val message: String,
    val code: String
)