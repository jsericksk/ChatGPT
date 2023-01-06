package com.kproject.chatgpt.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.domain.model.RecentChatModel
import java.util.*

@Entity(tableName = "recent_chats")
data class RecentChatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_id")
    val chatId: Long = 0,
    @ColumnInfo(name = "chat_name")
    val chatName: String,
    @ColumnInfo(name = "used_tokens")
    val usedTokens: Int,
    @ColumnInfo(name = "last_message")
    val lastMessage: String,
    @ColumnInfo(name = "last_message_date")
    val lastMessageDate: Date,
    @ColumnInfo(name = "last_message_sent_by_user")
    val lastMessageSentByUser: Boolean,
    @ColumnInfo(name = "chat_mode")
    val chatMode: Boolean,
    @ColumnInfo(name = "model_options")
    val aiModelOptions: AIModelOptions
)

fun RecentChatModel.fromModel() = RecentChatEntity(
    chatId = chatId,
    chatName = chatName,
    usedTokens = usedTokens,
    lastMessage = lastMessage,
    lastMessageDate = lastMessageDate,
    lastMessageSentByUser = lastMessageSentByUser,
    chatMode = chatMode,
    aiModelOptions = aiModelOptions
)

fun RecentChatEntity.toModel() = RecentChatModel(
    chatId = chatId,
    chatName = chatName,
    usedTokens = usedTokens,
    lastMessage = lastMessage,
    lastMessageDate = lastMessageDate,
    lastMessageSentByUser = lastMessageSentByUser,
    chatMode = chatMode,
    aiModelOptions = aiModelOptions
)