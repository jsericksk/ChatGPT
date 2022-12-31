package com.kproject.chatgpt.presentation.screens.chat

import com.kproject.chatgpt.presentation.model.Message
import com.kproject.chatgpt.presentation.model.RecentChat

data class ChatUiState(
    val messageList: List<Message> = emptyList(),
    val recentChat: RecentChat = RecentChat(),
    val message: String = ""
)