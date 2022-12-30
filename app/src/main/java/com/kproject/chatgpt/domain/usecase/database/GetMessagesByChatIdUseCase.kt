package com.kproject.chatgpt.domain.usecase.database

import com.kproject.chatgpt.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow

fun interface GetMessagesByChatIdUseCase {
    suspend operator fun invoke(chatId: Long): Flow<List<MessageModel>>
}