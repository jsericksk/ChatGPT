package com.kproject.chatgpt.data.database.converter

import androidx.room.ProvidedTypeConverter
import com.google.gson.Gson
import com.kproject.chatgpt.commom.model.AIModelOptions
import java.util.*
import androidx.room.TypeConverter

@ProvidedTypeConverter
class RoomTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromAIModelOptions(aiModelOptions: AIModelOptions): String {
        return gson.toJson(aiModelOptions)
    }

    @TypeConverter
    fun toAIModelOptions(json: String): AIModelOptions? {
        return gson.fromJson(json, AIModelOptions::class.java)
    }
}