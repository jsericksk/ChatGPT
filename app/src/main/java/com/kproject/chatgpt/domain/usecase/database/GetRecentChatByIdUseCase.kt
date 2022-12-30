package com.kproject.chatgpt.domain.usecase.database

import com.kproject.chatgpt.domain.model.RecentChatModel

fun interface GetRecentChatByIdUseCase {
    suspend operator fun invoke(chatId: Long): RecentChatModel
}