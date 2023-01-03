package com.kproject.chatgpt.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kproject.chatgpt.data.database.entity.MessageEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE chat_id = :chatId")
    fun getMessagesByChatId(chatId: Long): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessageEntity)

    @Query("DELETE FROM messages WHERE chat_id = :chatId")
    suspend fun deleteMessagesFromChatId(chatId: Long)
}