package com.kproject.chatgpt.data.api.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MessageBody(
    @SerializedName("model")
    val model: String,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("max_tokens")
    val maxTokens: Int,
    @SerializedName("temperature")
    val temperature: Float
)