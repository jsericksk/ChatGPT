package com.kproject.chatgpt.domain.usecase.preferences

fun interface SavePreferenceUseCase {
    suspend operator fun invoke(key: String, value: Any)
}