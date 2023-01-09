package com.kproject.chatgpt.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.chatgpt.commom.PrefsConstants
import com.kproject.chatgpt.domain.usecase.database.DeleteMessagesFromChatIdUseCase
import com.kproject.chatgpt.domain.usecase.database.DeleteRecentChatUseCase
import com.kproject.chatgpt.domain.usecase.database.GetAllRecentChatsUseCase
import com.kproject.chatgpt.domain.usecase.database.UpdateRecentChatUseCase
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceUseCase
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCase
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.model.fromModel
import com.kproject.chatgpt.presentation.model.toModel
import com.kproject.chatgpt.presentation.theme.custom.ThemeOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPreferenceUseCase: GetPreferenceUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val getAllRecentChatsUseCase: GetAllRecentChatsUseCase,
    private val updateRecentChatUseCase: UpdateRecentChatUseCase,
    private val deleteRecentChatUseCase: DeleteRecentChatUseCase,
    private val deleteMessagesFromChatIdUseCase: DeleteMessagesFromChatIdUseCase
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        getPreferences()
        getAllRecentChats()
    }

    private fun getPreferences() {
        val apiKey = getPreferenceUseCase(
            key = PrefsConstants.ApiKey,
            defaultValue = ""
        )
        val themeOption = getPreferenceUseCase(
            key = PrefsConstants.ThemeOption,
            defaultValue = ThemeOptions.DefaultTheme
        )
        val isDarkMode = getPreferenceUseCase(
            key = PrefsConstants.DarkMode,
            defaultValue = true
        )
        homeUiState = homeUiState.copy(
            apiKey = apiKey,
            themeOption = themeOption,
            isDarkMode = isDarkMode
        )
    }

    private fun getAllRecentChats() {
        viewModelScope.launch {
            homeUiState = homeUiState.copy(isLoading = true)
            getAllRecentChatsUseCase().collect { recentChatsModelList ->
                val recentChatsList = recentChatsModelList.map { recentChatModel ->
                    recentChatModel.fromModel()
                }
                homeUiState = homeUiState.copy(recentChatsList = recentChatsList, isLoading = false)
            }
        }
    }

    fun saveApiKey(apiKey: String) {
        savePreferenceUseCase(
            key = PrefsConstants.ApiKey,
            value = apiKey
        )
        homeUiState = homeUiState.copy(apiKey = apiKey)
    }

    fun renameRecentChat(newChatName: String, recentChat: RecentChat) {
        viewModelScope.launch {
            val updatedRecentChat = recentChat.copy(chatName = newChatName)
            updateRecentChatUseCase(updatedRecentChat.toModel())
        }
    }

    fun clearRecentChat(recentChat: RecentChat) {
        viewModelScope.launch {
            deleteMessagesFromChatIdUseCase(recentChat.chatId)
            val updatedRecentChat = recentChat.copy(
                usedTokens = 0,
                lastMessage = ""
            )
            updateRecentChatUseCase(updatedRecentChat.toModel())
        }
    }

    fun deleteRecentChat(recentChat: RecentChat) {
        viewModelScope.launch {
            deleteRecentChatUseCase(recentChat.toModel())
        }
    }

    fun changeThemeOption(themeOption: Int, isDarkMode: Boolean) {
        savePreferenceUseCase(
            key = PrefsConstants.ThemeOption,
            value = themeOption
        )
        savePreferenceUseCase(
            key = PrefsConstants.DarkMode,
            value = isDarkMode
        )
        homeUiState = homeUiState.copy(themeOption = themeOption, isDarkMode = isDarkMode)
    }
}