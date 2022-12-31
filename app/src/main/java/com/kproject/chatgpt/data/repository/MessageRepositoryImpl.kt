package com.kproject.chatgpt.data.repository

import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.data.api.ApiService
import com.kproject.chatgpt.data.api.entity.MessageBody
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.database.dao.RecentChatDao
import com.kproject.chatgpt.data.mapper.fromModel
import com.kproject.chatgpt.data.mapper.toModel
import com.kproject.chatgpt.domain.model.MessageModel
import com.kproject.chatgpt.domain.model.RecentChatModel
import com.kproject.chatgpt.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class MessageRepositoryImpl(
    private val messageDao: MessageDao,
    private val apiService: ApiService
) : MessageRepository {

    override fun getMessagesByChatId(chatId: Long): Flow<List<MessageModel>> {
        return messageDao.getMessagesByChatId(chatId).map { messageListEntity ->
            messageListEntity.map { messageEntity ->
                messageEntity.toModel()
            }
        }
    }

    override suspend fun addMessage(message: MessageModel) {
        messageDao.addMessage(message.fromModel())
    }

    // TODO: Get apiKey from preferences and better handle errors
    override suspend fun sendMessage(
        message: String,
        recentChat: RecentChatModel,
        apiKey: String
    ): DataState<MessageModel> {
        addMessageToDatabase(
            chatId = recentChat.chatId,
            message = message
        )

        val aiModelOptions = recentChat.aiModelOptions
        val messageBody = MessageBody(
            model = aiModelOptions.aiModel.value,
            prompt = message,
            maxTokens = aiModelOptions.maxTokens,
            temperature = aiModelOptions.temperature
        )

        val apiResponse = apiService.sendMessage(apiKey = "Bearer $apiKey", messageBody = messageBody)
        if (apiResponse.code() == 200) {
            return apiResponse.body()?.let { messageResponse ->
                val answerText = messageResponse.choices.first().text.trim()
                val messageModel = MessageModel(
                    chatId = recentChat.chatId,
                    message = answerText,
                    sentByUser = false,
                    sendDate = Date()
                )
                messageDao.addMessage(messageModel.fromModel())
                DataState.Success(messageModel)
            } ?: DataState.Error()
        }
        return DataState.Error()
    }

    private suspend fun addMessageToDatabase(chatId: Long, message: String) {
        val messageModel = MessageModel(
            chatId = chatId,
            message = message,
            sentByUser = true,
            sendDate = Date()
        )
        addMessage(messageModel)
    }
}