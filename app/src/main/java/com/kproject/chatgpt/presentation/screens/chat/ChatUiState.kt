package com.kproject.chatgpt.presentation.screens.chat

import com.kproject.chatgpt.presentation.model.Chat
import com.kproject.chatgpt.presentation.model.RecentChat

data class ChatUiState(
    val chatList: List<Chat> = emptyList(),
    val recentChat: RecentChat = RecentChat(),
    val message: String = ""
)