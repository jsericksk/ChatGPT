package com.kproject.chatgpt.presentation.model

data class ModelOptions(
    val iaModel: IAModelOption = IAModelOption.TextDavinci003,
    val maxTokens: Int = 700,
    val temperature: Float = 0F
)

enum class IAModelOption {
    TextDavinci003,
    TextCurie001
}