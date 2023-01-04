package com.kproject.chatgpt.domain.usecase.preferences

interface SavePreferenceUseCase {
    operator fun <T> invoke(key: String, value: T)
}