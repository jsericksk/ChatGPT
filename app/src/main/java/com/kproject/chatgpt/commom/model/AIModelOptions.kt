package com.kproject.chatgpt.commom.model

data class AIModelOptions(
    val aiModel: AIModel = AIModel.TextDavinci003,
    val maxTokens: Int = 700,
    val temperature: Float = 0F
)

enum class AIModel(val value: String) {
    TextDavinci003("text-davinci-003"),
    TextCurie001("text-curi-001")
}