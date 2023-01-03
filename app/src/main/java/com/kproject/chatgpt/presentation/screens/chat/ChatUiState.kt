package com.kproject.chatgpt.presentation.screens.chat

import androidx.annotation.StringRes
import com.kproject.chatgpt.presentation.model.Message
import com.kproject.chatgpt.presentation.model.RecentChat

data class ChatUiState(
    val message: String = "",
    val isLoading: Boolean = false,
    val messageList: List<Message> = emptyList(),
    val recentChat: RecentChat = RecentChat(),
    val apiResponseErrorInfo: ApiResponseErrorInfo? = null
)

data class ApiResponseErrorInfo(
   @StringRes val titleResId: Int,
   @StringRes val descriptionResId: Int
)