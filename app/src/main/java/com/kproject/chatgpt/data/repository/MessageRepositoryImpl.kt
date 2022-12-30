package com.kproject.chatgpt.data.repository

import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.data.api.ApiService
import com.kproject.chatgpt.data.api.entity.MessageBody
import com.kproject.chatgpt.data.database.dao.MessageDao
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

    override suspend fun sendMessage(
        message: String,
        recentChat: RecentChatModel
    ): DataState<MessageModel> {
        val aiModelOptions = recentChat.aiModelOptions
        val messageBody = MessageBody(
            model = aiModelOptions.aiModel.value,
            prompt = message,
            maxTokens = aiModelOptions.maxTokens,
            temperature = aiModelOptions.temperature
        )
        val apiKey = ""
        val apiResponse = apiService.sendMessage(apiKey = apiKey, messageBody = messageBody)
        if (apiResponse.code() == 200) {
            apiResponse.body()?.let { messageResponse ->
                val messageModel = MessageModel(
                    chatId = recentChat.chatId,
                    message = messageResponse.choices.first().text,
                    sentByUser = false,
                    sendDate = Date()
                )
                return DataState.Success(messageModel)
            }
        }
        return DataState.Error()
    }
}