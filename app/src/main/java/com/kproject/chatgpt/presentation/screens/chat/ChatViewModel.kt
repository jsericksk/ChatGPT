package com.kproject.chatgpt.presentation.screens.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.domain.usecase.api.SendMessageUseCase
import com.kproject.chatgpt.domain.usecase.database.*
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
    private val addMessageUseCase: AddMessageUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var chatUiState by mutableStateOf(ChatUiState())
        private set

    private val chatArgs =
            checkNotNull(savedStateHandle[ArgChatArgs]).toString().fromJson(ChatArgs::class.java)

    init {
        initializeChat()
    }

    private fun initializeChat() {
        viewModelScope.launch {
            chatUiState = chatUiState.copy(isLoading = true)
            var recentChat = RecentChat(chatName = chatArgs.chatName, chatMode = isChatMode())
            var chatId = chatArgs.chatId
            if (chatId == UnspecifiedChatId) {
                chatId = addRecentChatUseCase(recentChat.toModel())
                recentChat = recentChat.copy(chatId = chatId)
            } else {
                recentChat = getRecentChatByIdUseCase(chatArgs.chatId).fromModel()
            }

            getMessages(chatId = chatId)
            chatUiState = chatUiState.copy(recentChat = recentChat)
        }
    }

    private fun getMessages(chatId: Long) {
        viewModelScope.launch {
            getMessagesByChatIdUseCase(chatId).collect { messageModelList ->
                val messageList = messageModelList.map { messageModel ->
                    messageModel.fromModel()
                }
                chatUiState = chatUiState.copy(messageList = messageList, isLoading = false)
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            if (message.isNotBlank()) {
                onMessageValueChange(message = "")
                addMessageToDatabase(message)
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

        val text = StringBuilder()
        currentMessageList.forEach { message ->
            text.append(message.message + "\n\n")
        }
        return text.toString() + messageText
    }

    private fun addMessageToDatabase(textMessage: String) {
        viewModelScope.launch {
            val message = Message(
                chatId = chatUiState.recentChat.chatId,
                message = textMessage,
                sentByUser = true,
                sendDate = Date()
            )
            addMessageUseCase(message.toModel())
            updateRecentChat(
                sumTokens = false,
                lastMessage = textMessage,
                lastMessageSentByUser = true
            )
        }
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
        return chatArgs.isChatMode
    }

    fun updateAIModelOptions(aiModelOptions: AIModelOptions) {
        viewModelScope.launch {
            val currentRecentChat = chatUiState.recentChat
            val updatedRecentChat = currentRecentChat.copy(aiModelOptions = aiModelOptions)
            updateRecentChatUseCase(updatedRecentChat.toModel())
            chatUiState = chatUiState.copy(recentChat = updatedRecentChat)
        }
    }

    fun onMessageValueChange(message: String) {
        chatUiState = chatUiState.copy(message = message)
    }
}