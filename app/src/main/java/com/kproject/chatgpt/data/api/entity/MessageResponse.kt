package com.kproject.chatgpt.data.api.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MessageResponse(
    val choices: List<TextMessageResponse>,
    val usage: UsageResponse
)

@Keep
data class TextMessageResponse(
    val text: String
)

@Keep
data class UsageResponse(
    @SerializedName("total_tokens")
    val totalTokens: Int
)