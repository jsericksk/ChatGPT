package com.kproject.chatgpt.presentation.screens.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.domain.usecase.api.SendMessageUseCase
import com.kproject.chatgpt.domain.usecase.database.AddRecentChatUseCase
import com.kproject.chatgpt.domain.usecase.database.GetMessagesByChatIdUseCase
import com.kproject.chatgpt.domain.usecase.database.GetRecentChatByIdUseCase
import com.kproject.chatgpt.domain.usecase.database.UpdateRecentChatUseCase
import com.kproject.chatgpt.presentation.model.*
import com.kproject.chatgpt.presentation.navigation.ArgApiKey
import com.kproject.chatgpt.presentation.navigation.ArgChatId
import com.kproject.chatgpt.presentation.navigation.ArgConversationModeKey
import com.kproject.chatgpt.presentation.navigation.NullChatId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "ChatViewModel"

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesByChatIdUseCase: GetMessagesByChatIdUseCase,
    private val getRecentChatByIdUseCase: GetRecentChatByIdUseCase,
    private val addRecentChatUseCase: AddRecentChatUseCase,
    private val updateRecentChatUseCase: UpdateRecentChatUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var chatUiState by mutableStateOf(ChatUiState(messageList = fakeChatList))
        private set

    private val chatId: Long = checkNotNull(savedStateHandle[ArgChatId])
    private val apiKey: String = checkNotNull(savedStateHandle[ArgApiKey])
    private val conversationMode: Int = checkNotNull(savedStateHandle[ArgConversationModeKey])

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            chatUiState = chatUiState.copy(isLoading = true)
            if (chatId != NullChatId) {
                getMessages()
            } else {
                createChat()
            }
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            val recentChat = getRecentChatByIdUseCase(chatId)
            chatUiState = chatUiState.copy(recentChat = recentChat.fromModel())
            getMessagesByChatIdUseCase(chatId).collect { messageModelList ->
                val messageList = messageModelList.map { messageModel ->
                    messageModel.fromModel()
                }
                chatUiState = chatUiState.copy(messageList = messageList, isLoading = false)
            }
        }
    }

    private fun createChat() {
        viewModelScope.launch {
            val chatMode = ConversationMode.fromValue(conversationMode) == ConversationMode.ChatMode
            val recentChat = RecentChat(
                chatId = 0,
                chatName = "Test",
                usedTokens = 0,
                lastMessage = "",
                lastMessageDate = Date(),
                lastMessageSentByUser = true,
                chatMode = chatMode,
                aiModelOptions = AIModelOptions()
            )
            addRecentChatUseCase(recentChat.toModel())
            // todo: observe id of this...
            chatUiState = chatUiState.copy(recentChat = recentChat)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            if (message.isNotBlank()) {
                onMessageValueChange(message = "")

                val response = sendMessageUseCase(
                    message = message,
                    recentChat = chatUiState.recentChat.toModel(),
                    apiKey = apiKey
                )
                when (response) {
                    is DataState.Success -> {
                        response.data?.let { messageData ->
                            val usedTokens = sumUsedTokens(messageData.totalTokens)
                            val currentRecentChat = chatUiState.recentChat
                            val updatedRecentChat = currentRecentChat.copy(usedTokens = usedTokens)
                            chatUiState = chatUiState.copy(recentChat = updatedRecentChat)
                            updateRecentChatUseCase(updatedRecentChat.toModel())
                        }
                    }
                    is DataState.Error -> {

                    }
                    else -> {}
                }
            }
        }
    }

    private fun sumUsedTokens(tokens: Int): Int {
        val isChatMode = ConversationMode.fromValue(conversationMode) == ConversationMode.ChatMode
        val currentUsedTokens = chatUiState.recentChat.usedTokens
        if (isChatMode) {
            return tokens
        }
        return currentUsedTokens + tokens
    }

    fun onMessageValueChange(message: String) {
        chatUiState = chatUiState.copy(message = message)
    }

}