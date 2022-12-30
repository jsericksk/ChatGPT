package com.kproject.chatgpt.data.api.entity

import com.google.gson.annotations.SerializedName

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