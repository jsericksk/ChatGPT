package com.kproject.chatgpt.data.repository

import com.kproject.chatgpt.data.database.dao.RecentChatDao
import com.kproject.chatgpt.data.mapper.fromModel
import com.kproject.chatgpt.data.mapper.toModel
import com.kproject.chatgpt.domain.model.RecentChatModel
import com.kproject.chatgpt.domain.repository.RecentChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentChatRepositoryImpl(
    private val recentChatDao: RecentChatDao
) : RecentChatRepository {

    override fun getAllRecentChats(): Flow<List<RecentChatModel>> {
        return recentChatDao.getAllRecentChats().map { recentChatListEntity ->
            recentChatListEntity.map { recentChatEntity ->
                recentChatEntity.toModel()
            }
        }
    }

    override suspend fun getRecentChatById(chatId: Long): RecentChatModel {
        return recentChatDao.getRecentChatById(chatId).toModel()
    }

    override suspend fun addRecentChat(recentChat: RecentChatModel) {
        recentChatDao.addRecentChat(recentChat.fromModel())
    }

    override suspend fun updateRecentChat(recentChat: RecentChatModel) {
        recentChatDao.updateRecentChat(recentChat.fromModel())
    }

    override suspend fun deleteRecentChat(recentChat: RecentChatModel) {
        recentChatDao.deleteRecentChat(recentChat.fromModel())
    }
}