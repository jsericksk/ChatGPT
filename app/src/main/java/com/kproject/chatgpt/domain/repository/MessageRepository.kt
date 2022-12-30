package com.kproject.chatgpt.domain.repository

import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getMessagesByChatId(chatId: Long): Flow<List<MessageModel>>

    suspend fun addMessage(message: MessageModel)

    suspend fun sendMessage(message: MessageModel, aiModelOptions: AIModelOptions)
}