package com.kproject.chatgpt.presentation.di

import com.kproject.chatgpt.domain.repository.MessageRepository
import com.kproject.chatgpt.domain.repository.RecentChatRepository
import com.kproject.chatgpt.domain.usecase.api.SendMessageUseCase
import com.kproject.chatgpt.domain.usecase.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseDomainModule {


    // RecentMessage
    @Provides
    @Singleton
    fun provideGetAllRecentChatsUseCase(recentChatRepository: RecentChatRepository): GetAllRecentChatsUseCase {
        return GetAllRecentChatsUseCase(recentChatRepository::getAllRecentChats)
    }

    @Provides
    @Singleton
    fun provideGetRecentChatByIdUseCase(recentChatRepository: RecentChatRepository): GetRecentChatByIdUseCase {
        return GetRecentChatByIdUseCase(recentChatRepository::getRecentChatById)
    }

    @Provides
    @Singleton
    fun provideAddRecentChatUseCase(recentChatRepository: RecentChatRepository): AddRecentChatUseCase {
        return AddRecentChatUseCase(recentChatRepository::addRecentChat)
    }

    @Provides
    @Singleton
    fun provideDeleteRecentChatUseCase(recentChatRepository: RecentChatRepository): DeleteRecentChatUseCase {
        return DeleteRecentChatUseCase(recentChatRepository::deleteRecentChat)
    }

    @Provides
    @Singleton
    fun provideUpdateRecentChatUseCase(recentChatRepository: RecentChatRepository): UpdateRecentChatUseCase {
        return UpdateRecentChatUseCase(recentChatRepository::updateRecentChat)
    }

    // Message
    @Provides
    @Singleton
    fun provideAddMessageUseCase(messageRepository: MessageRepository): AddMessageUseCase {
        return AddMessageUseCase(messageRepository::addMessage)
    }

    @Provides
    @Singleton
    fun provideGetMessagesByChatIdUseCase(messageRepository: MessageRepository): GetMessagesByChatIdUseCase {
        return GetMessagesByChatIdUseCase(messageRepository::getMessagesByChatId)
    }

    @Provides
    @Singleton
    fun provideSendMessageUseCase(messageRepository: MessageRepository): SendMessageUseCase {
        return SendMessageUseCase(messageRepository::sendMessage)
    }
}