package com.kproject.chatgpt.domain.usecase.preferences

import com.kproject.chatgpt.domain.repository.PreferenceRepository

class SavePreferenceUseCaseImpl(
    private val preferenceRepository: PreferenceRepository
) : SavePreferenceUseCase {

    override fun <T> invoke(key: String, value: T) {
        preferenceRepository.savePreference(key, value)
    }
}