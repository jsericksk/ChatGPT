package com.kproject.chatgpt.data.api.entity

import androidx.annotation.Keep

@Keep
data class ErrorResponse(
    val error: Error
)

@Keep
data class Error(
    val message: String,
    val code: String
)