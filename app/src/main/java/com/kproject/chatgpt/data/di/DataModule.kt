package com.kproject.chatgpt.data.di

import android.content.Context
import com.kproject.chatgpt.data.repository.PreferenceRepositoryImpl
import com.kproject.chatgpt.domain.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext applicationContext: Context): PreferenceRepository {
        return PreferenceRepositoryImpl(applicationContext)
    }
}
