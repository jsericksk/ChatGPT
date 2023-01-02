package com.kproject.chatgpt.domain.usecase.database

import com.kproject.chatgpt.domain.model.RecentChatModel

fun interface AddRecentChatUseCase {
    suspend operator fun invoke(recentChat: RecentChatModel): Long
}