package com.kproject.chatgpt.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.chatgpt.commom.PrefsConstants
import com.kproject.chatgpt.domain.usecase.database.DeleteRecentChatUseCase
import com.kproject.chatgpt.domain.usecase.database.GetAllRecentChatsUseCase
import com.kproject.chatgpt.domain.usecase.database.UpdateRecentChatUseCase
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceAsyncUseCase
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCase
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.model.fakeRecentChatsList
import com.kproject.chatgpt.presentation.model.fromModel
import com.kproject.chatgpt.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPreferenceAsyncUseCase: GetPreferenceAsyncUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase,
    private val getAllRecentChatsUseCase: GetAllRecentChatsUseCase,
    private val updateRecentChatUseCase: UpdateRecentChatUseCase,
    private val deleteRecentChatUseCase: DeleteRecentChatUseCase
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState(recentChatsList = fakeRecentChatsList))
        private set

    init {
        collectApiKey()
        getAllRecentChats()
    }

    private fun collectApiKey() {
        viewModelScope.launch {
            getPreferenceAsyncUseCase(
                key = PrefsConstants.ApiKey,
                defaultValue = ""
            ).collectLatest {
                homeUiState = homeUiState.copy(apiKey = it as String)
            }
        }
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
        viewModelScope.launch {
            savePreferenceUseCase(
                key = PrefsConstants.ApiKey,
                value = apiKey
            )
        }
    }

    fun renameRecentChat(newChatName: String, recentChat: RecentChat) {
        viewModelScope.launch {
            val updatedRecentChat = recentChat.copy(chatName = newChatName)
            updateRecentChatUseCase(updatedRecentChat.toModel())
        }
    }

    fun deleteRecentChat(recentChat: RecentChat) {
        viewModelScope.launch {
            deleteRecentChatUseCase(recentChat.toModel())
        }
    }
}