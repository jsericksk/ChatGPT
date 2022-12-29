package com.kproject.chatgpt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kproject.chatgpt.data.database.converter.RoomTypeConverter
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.database.dao.RecentChatDao
import com.kproject.chatgpt.data.database.entity.MessageEntity
import com.kproject.chatgpt.data.database.entity.RecentChatEntity

@Database(entities = [RecentChatEntity::class, MessageEntity::class], version = 1)
@TypeConverters(RoomTypeConverter::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun recentChatDao(): RecentChatDao

    abstract fun messageDao(): MessageDao
}