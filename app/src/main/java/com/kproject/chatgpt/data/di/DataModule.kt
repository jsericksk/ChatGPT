package com.kproject.chatgpt.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.kproject.chatgpt.data.api.ApiService
import com.kproject.chatgpt.data.database.ChatDatabase
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.database.dao.RecentChatDao
import com.kproject.chatgpt.data.repository.PreferenceRepositoryImpl
import com.kproject.chatgpt.data.repository.RecentChatRepositoryImpl
import com.kproject.chatgpt.domain.repository.PreferenceRepository
import com.kproject.chatgpt.domain.repository.RecentChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePreferenceRepository(@ApplicationContext applicationContext: Context): PreferenceRepository {
        return PreferenceRepositoryImpl(applicationContext)
    }

    // Database
    @Provides
    @Singleton
    fun provideChatDatabase(application: Application): ChatDatabase {
        return Room.databaseBuilder(
            application,
            ChatDatabase::class.java,
            "chat_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecentChatDao(chatDatabase: ChatDatabase): RecentChatDao {
        return chatDatabase.recentChatDao()
    }

    @Provides
    @Singleton
    fun provideMessageChatDao(chatDatabase: ChatDatabase): MessageDao {
        return chatDatabase.messageDao()
    }

    @Provides
    @Singleton
    fun provideRecentChatRepository(
        recentChatDao: RecentChatDao
    ): RecentChatRepository {
        return RecentChatRepositoryImpl(recentChatDao)
    }

    // API
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiService.API_BASE_URL)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
