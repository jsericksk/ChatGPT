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
import com.kproject.chatgpt.presentation.extensions.fromJson
import com.kproject.chatgpt.presentation.model.*
import com.kproject.chatgpt.presentation.navigation.ArgChatArgs
import dagger.hilt.android.lifecycle.HiltViewModel
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
    var chatUiState by mutableStateOf(ChatUiState())
        private set

    private val chatArgs =
            checkNotNull(savedStateHandle[ArgChatArgs]).toString().fromJson(ChatArgs::class.java)

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            chatUiState = chatUiState.copy(isLoading = true)
            if (chatArgs.chatId != UnspecifiedChatId) {
                getMessages()
            } else {
                createChat()
            }
        }
    }

    private fun getMessages() {
        viewModelScope.launch {
            val recentChat = getRecentChatByIdUseCase(chatArgs.chatId)
            chatUiState = chatUiState.copy(recentChat = recentChat.fromModel())
            getMessagesByChatIdUseCase(chatArgs.chatId).collect { messageModelList ->
                val messageList = messageModelList.map { messageModel ->
                    messageModel.fromModel()
                }
                chatUiState = chatUiState.copy(messageList = messageList, isLoading = false)
            }
        }
    }

    private fun createChat() {
        viewModelScope.launch {
            val recentChat = RecentChat(
                chatName = chatArgs.chatName,
                usedTokens = 0,
                lastMessage = "",
                lastMessageDate = Date(),
                lastMessageSentByUser = true,
                chatMode = isChatMode(),
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
                updateRecentChat(
                    sumTokens = false,
                    lastMessage = message,
                    lastMessageSentByUser = false
                )
                val messageText = generateMessageToSend(message)
                val response = sendMessageUseCase(
                    message = messageText,
                    recentChat = chatUiState.recentChat.toModel(),
                    apiKey = chatArgs.apiKey
                )

                when (response) {
                    is DataState.Success -> {
                        response.data?.let { messageData ->
                            updateRecentChat(
                                usedTokens = messageData.totalTokens,
                                sumTokens = true,
                                lastMessage = messageData.message,
                                lastMessageSentByUser = false
                            )
                        }
                    }
                    is DataState.Error -> {

                    }
                    else -> {}
                }
            }
        }
    }

    private fun generateMessageToSend(messageText: String): String {
        val currentMessageList = chatUiState.messageList
        if (!isChatMode() || currentMessageList.isEmpty()) {
            return messageText
        }

        val text = StringBuilder(messageText + "\n\n")
        currentMessageList.forEach { message ->
            text.append(message.message + "\n\n")
        }
        return text.toString()
    }

    private fun updateRecentChat(
        usedTokens: Int = 0,
        sumTokens: Boolean,
        lastMessage: String,
        lastMessageSentByUser: Boolean,
    ) {
        viewModelScope.launch {
            val currentRecentChat = chatUiState.recentChat
            val totalUsedTokens = if (sumTokens) {
                sumUsedTokens(usedTokens)
            } else {
                currentRecentChat.usedTokens
            }
            val updatedRecentChat = currentRecentChat.copy(
                usedTokens = totalUsedTokens,
                lastMessage = lastMessage,
                lastMessageDate = Date(),
                lastMessageSentByUser = lastMessageSentByUser
            )
            chatUiState = chatUiState.copy(recentChat = updatedRecentChat)
            updateRecentChatUseCase(updatedRecentChat.toModel())
        }
    }

    private fun sumUsedTokens(tokens: Int): Int {
        if (isChatMode()) {
            return tokens
        }
        val currentUsedTokens = chatUiState.recentChat.usedTokens
        return currentUsedTokens + tokens
    }

    private fun isChatMode(): Boolean {
        return ConversationMode.fromValue(chatArgs.conversationMode) == ConversationMode.ChatMode
    }

    fun onMessageValueChange(message: String) {
        chatUiState = chatUiState.copy(message = message)
    }

}