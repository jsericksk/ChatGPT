package com.kproject.chatgpt.domain.usecase.database

import com.kproject.chatgpt.domain.model.RecentChatModel
import kotlinx.coroutines.flow.Flow

fun interface GetAllRecentChatsUseCase {
    suspend operator fun invoke(): Flow<List<RecentChatModel>>
}