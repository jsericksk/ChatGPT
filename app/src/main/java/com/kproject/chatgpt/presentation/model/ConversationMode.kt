package com.kproject.chatgpt.presentation.model

enum class ConversationMode(val value: Int) {
    ChatMode(0),
    SearchMode(1),
    None(2);

    companion object {
        fun fromValue(value: Int): ConversationMode {
           return values().first { it.value == value }
        }
    }
}