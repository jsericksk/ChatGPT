package com.kproject.chatgpt.domain.usecase.preferences

import com.kproject.chatgpt.domain.repository.PreferenceRepository

class GetPreferenceUseCaseImpl(
    private val preferenceRepository: PreferenceRepository
) : GetPreferenceUseCase {

    override fun <T> invoke(key: String, defaultValue: T): T {
        return preferenceRepository.getPreference(key, defaultValue)
    }
}