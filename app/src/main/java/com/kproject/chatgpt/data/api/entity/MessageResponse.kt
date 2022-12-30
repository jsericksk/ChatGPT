package com.kproject.chatgpt.data.api.entity

data class MessageResponse(
    val choices: List<TextMessageResponse>,
)

data class TextMessageResponse(
    val text: String
)