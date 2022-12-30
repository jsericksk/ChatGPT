package com.kproject.chatgpt.domain.model

import java.util.*

data class MessageModel(
    val id: Long,
    val chatId: Long,
    val message: String,
    val sentByUser: Boolean,
    val sendDate: Date
)