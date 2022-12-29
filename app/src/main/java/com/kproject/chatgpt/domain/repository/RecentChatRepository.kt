package com.kproject.chatgpt.domain.repository

import com.kproject.chatgpt.domain.model.RecentChatModel
import kotlinx.coroutines.flow.Flow

interface RecentChatRepository {

    fun getAllRecentChats(): Flow<List<RecentChatModel>>

    suspend fun getRecentChatById(chatId: Long): RecentChatModel

    suspend fun addRecentChat(recentChat: RecentChatModel)

    suspend fun updateRecentChat(recentChat: RecentChatModel)

    suspend fun deleteRecentChat(recentChat: RecentChatModel)
}