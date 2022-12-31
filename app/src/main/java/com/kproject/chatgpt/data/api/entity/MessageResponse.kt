package com.kproject.chatgpt.data.api.entity

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    val choices: List<TextMessageResponse>,
    val usage: UsageResponse
)

data class TextMessageResponse(
    val text: String
)

data class UsageResponse(
    @SerializedName("total_tokens")
    val totalTokens: Int
)