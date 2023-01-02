package com.kproject.chatgpt.data.database.dao

import androidx.room.*
import com.kproject.chatgpt.data.database.entity.RecentChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentChatDao {

    @Query("SELECT * FROM recent_chats")
    fun getAllRecentChats(): Flow<List<RecentChatEntity>>

    @Query("SELECT * FROM recent_chats WHERE chat_id = :chatId")
    suspend fun getRecentChatById(chatId: Long): RecentChatEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecentChat(recentChat: RecentChatEntity): Long

    @Update
    suspend fun updateRecentChat(recentChat: RecentChatEntity)

    @Delete
    suspend fun deleteRecentChat(recentChat: RecentChatEntity)
}