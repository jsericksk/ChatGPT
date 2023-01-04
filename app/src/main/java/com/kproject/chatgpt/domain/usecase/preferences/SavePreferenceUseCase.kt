package com.kproject.chatgpt.domain.usecase.preferences

fun interface SavePreferenceUseCase {
    operator fun invoke(key: String, value: Any)
}