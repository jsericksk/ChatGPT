package com.kproject.chatgpt.presentation.di

import com.kproject.chatgpt.domain.repository.PreferenceRepository
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceUseCase
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceUseCaseImpl
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCase
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCaseImpl
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
    fun provideGetPreferenceUseCase(preferenceRepository: PreferenceRepository): GetPreferenceUseCase {
        return GetPreferenceUseCaseImpl(preferenceRepository)
    }

    @Provides
    @Singleton
    fun provideSavePreferenceUseCase(preferenceRepository: PreferenceRepository): SavePreferenceUseCase {
        return SavePreferenceUseCaseImpl(preferenceRepository)
    }
}