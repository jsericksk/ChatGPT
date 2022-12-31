package com.kproject.chatgpt.presentation.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kproject.chatgpt.domain.usecase.api.SendMessageUseCase
import com.kproject.chatgpt.domain.usecase.database.GetMessagesByChatIdUseCase
import com.kproject.chatgpt.domain.usecase.database.GetRecentChatByIdUseCase
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.model.fakeChatList
import com.kproject.chatgpt.presentation.navigation.ArgApiKey
import com.kproject.chatgpt.presentation.navigation.ArgChatId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ChatViewModel"

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getRecentChatByIdUseCase: GetRecentChatByIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var chatUiState by mutableStateOf(ChatUiState(messageList = fakeChatList))
        private set

    private val chatId: Long = checkNotNull(savedStateHandle[ArgChatId])
    private val apiKey: String = checkNotNull(savedStateHandle[ArgApiKey])

    init {

    }

    private fun getMessagesByChatId() {

    }

    fun sendMessage(
        message: String,
        recentChat: RecentChat,
        apiKey: String
    ) {

    }

}