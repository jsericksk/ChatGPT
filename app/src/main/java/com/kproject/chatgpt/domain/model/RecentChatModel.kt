package com.kproject.chatgpt.domain.model

import com.kproject.chatgpt.commom.model.AIModelOptions

data class RecentChatModel(
    val chatId: Long,
    val chatName: String,
    val usedTokens: Int,
    val lastMessage: String,
    val lastMessageDate: String,
    val lastMessageSentByUser: Boolean,
    val chatMode: Boolean,
    val aiModelOptions: AIModelOptions
)