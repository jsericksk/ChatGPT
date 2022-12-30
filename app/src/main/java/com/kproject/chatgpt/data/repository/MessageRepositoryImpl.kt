package com.kproject.chatgpt.data.repository

import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.mapper.fromModel
import com.kproject.chatgpt.data.mapper.toModel
import com.kproject.chatgpt.domain.model.MessageModel
import com.kproject.chatgpt.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageRepositoryImpl(
    private val messageDao: MessageDao
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

    override suspend fun sendMessage(message: MessageModel, aiModelOptions: AIModelOptions) {
        TODO("Not yet implemented")
    }
}