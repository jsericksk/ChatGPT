package com.kproject.chatgpt.presentation.di

import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceUseCase
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceUseCaseImpl
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCase
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesDomainModule {

    @Binds
    abstract fun bindGetPreferenceUseCase(
        getPreferenceUseCaseImpl: GetPreferenceUseCaseImpl
    ): GetPreferenceUseCase

    @Binds
    abstract fun bindSavePreferenceUseCase(
        savePreferenceUseCaseImpl: SavePreferenceUseCaseImpl
    ): SavePreferenceUseCase
}