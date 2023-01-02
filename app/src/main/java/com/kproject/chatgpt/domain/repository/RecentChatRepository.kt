package com.kproject.chatgpt.domain.repository

import com.kproject.chatgpt.domain.model.RecentChatModel
import kotlinx.coroutines.flow.Flow

interface RecentChatRepository {

    fun getAllRecentChats(): Flow<List<RecentChatModel>>

    suspend fun getRecentChatById(chatId: Long): RecentChatModel

    suspend fun addRecentChat(recentChat: RecentChatModel): Long

    suspend fun updateRecentChat(recentChat: RecentChatModel): Long

    suspend fun deleteRecentChat(recentChat: RecentChatModel): Long
}