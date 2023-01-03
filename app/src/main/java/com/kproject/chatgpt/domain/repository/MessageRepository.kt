package com.kproject.chatgpt.domain.repository

import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.domain.model.MessageModel
import com.kproject.chatgpt.domain.model.RecentChatModel
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getMessagesByChatId(chatId: Long): Flow<List<MessageModel>>

    suspend fun addMessage(message: MessageModel)

    suspend fun sendMessage(
        message: String,
        recentChat: RecentChatModel,
        apiKey: String
    ): DataState<MessageModel>

    suspend fun deleteMessagesFromChatId(chatId: Long)
}