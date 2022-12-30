package com.kproject.chatgpt.domain.usecase.database

import com.kproject.chatgpt.domain.model.RecentChatModel

fun interface UpdateRecentChatUseCase {
    suspend operator fun invoke(recentChat: RecentChatModel)
}