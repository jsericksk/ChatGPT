package com.kproject.chatgpt.presentation.screens.home

import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.theme.custom.ThemeOptions

data class HomeUiState(
    val isLoading: Boolean = false,
    val recentChatsList: List<RecentChat> = emptyList(),
    val apiKey: String = "",
    val themeOption: Int = ThemeOptions.Option1
)