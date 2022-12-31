package com.kproject.chatgpt.presentation.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kproject.chatgpt.domain.usecase.api.SendMessageUseCase
import com.kproject.chatgpt.domain.usecase.database.GetMessagesByChatIdUseCase
import com.kproject.chatgpt.presentation.model.fakeChatList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ChatViewModel"

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {
    var chatUiState by mutableStateOf(ChatUiState(messageList = fakeChatList))
        private set



}