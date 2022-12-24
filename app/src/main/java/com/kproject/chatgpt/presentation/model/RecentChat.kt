package com.kproject.chatgpt.presentation.model

data class RecentChat(
    val chatId: Long,
    val chatName: String,
    val usedTokens: Int,
    val lastMessage: String,
    val createdAt: String,
)