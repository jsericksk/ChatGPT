package com.kproject.chatgpt.domain.usecase.preferences

fun interface GetPreferenceUseCase {
    operator fun invoke(key: String, defaultValue: Any): Any
}