package com.kproject.chatgpt.presentation.model

data class AIModelOptions(
    val iaModel: AIModel = AIModel.TextDavinci003,
    val maxTokens: Int = 700,
    val temperature: Float = 0F
)

enum class AIModel {
    TextDavinci003,
    TextCurie001
}