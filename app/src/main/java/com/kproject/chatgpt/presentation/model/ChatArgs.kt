package com.kproject.chatgpt.presentation.model

const val UnspecifiedChatId = -1L
const val UnspecifiedChatName = "unspecifiedChatName"

data class ChatArgs(
    val chatId: Long,
    val apiKey: String,
    val chatName: String,
    val isChatMode: Boolean
)