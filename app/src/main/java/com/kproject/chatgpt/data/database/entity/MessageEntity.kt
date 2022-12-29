package com.kproject.chatgpt.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "messages",
    foreignKeys = [ForeignKey(
        entity = RecentChatEntity::class,
        parentColumns = ["chat_id"],
        childColumns = ["chat_id"],
        onDelete = CASCADE
    )]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "chat_id")
    val chatId: Long,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "sent_by_user")
    val sentByUser: Boolean,
    @ColumnInfo(name = "send_date")
    val sendDate: Date
)