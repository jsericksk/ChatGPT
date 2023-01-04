package com.kproject.chatgpt.domain.usecase.preferences

interface GetPreferenceUseCase {
    operator fun <T> invoke(key: String, defaultValue: T): T
}