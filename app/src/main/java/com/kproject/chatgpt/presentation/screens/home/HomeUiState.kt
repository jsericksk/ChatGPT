package com.kproject.chatgpt.presentation.screens.home

import com.kproject.chatgpt.presentation.model.RecentChat

data class HomeUiState(
    val isLoading: Boolean = false,
    val recentChatsList: List<RecentChat> = emptyList()
)