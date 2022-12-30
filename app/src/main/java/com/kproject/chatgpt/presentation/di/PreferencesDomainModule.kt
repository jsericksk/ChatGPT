package com.kproject.chatgpt.presentation.di

import com.kproject.chatgpt.domain.repository.PreferenceRepository
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceAsyncUseCase
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceSyncUseCase
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesDomainModule {

    @Provides
    @Singleton
    fun provideGetPreferenceAsyncUseCase(preferenceRepository: PreferenceRepository): GetPreferenceAsyncUseCase {
        return GetPreferenceAsyncUseCase(preferenceRepository::getPreferenceAsync)
    }

    @Provides
    @Singleton
    fun provideGetPreferenceSyncUseCase(preferenceRepository: PreferenceRepository): GetPreferenceSyncUseCase {
        return GetPreferenceSyncUseCase(preferenceRepository::getPreferenceSync)
    }

    @Provides
    @Singleton
    fun provideSavePreferenceUseCase(preferenceRepository: PreferenceRepository): SavePreferenceUseCase {
        return SavePreferenceUseCase(preferenceRepository::savePreference)
    }
}