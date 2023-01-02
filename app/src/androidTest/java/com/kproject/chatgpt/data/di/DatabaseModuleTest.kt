package com.kproject.chatgpt.data.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.kproject.chatgpt.data.database.ChatDatabase
import com.kproject.chatgpt.data.database.converter.RoomTypeConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleTest {

    @Provides
    @Singleton
    @Named("test_gson")
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Named("test_database")
    fun provideTestDatabase(
        @ApplicationContext context: Context,
        gson: Gson
    ): ChatDatabase {
        return Room.inMemoryDatabaseBuilder(context, ChatDatabase::class.java)
            .allowMainThreadQueries()
            .addTypeConverter(RoomTypeConverter(gson))
            .build()
    }
}