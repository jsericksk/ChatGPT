package com.kproject.chatgpt.domain.usecase.api

import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.domain.model.MessageModel
import com.kproject.chatgpt.domain.model.RecentChatModel

fun interface SendMessageUseCase {

    suspend operator fun invoke(
        message: String,
        recentChat: RecentChatModel
    ): DataState<MessageModel>
}