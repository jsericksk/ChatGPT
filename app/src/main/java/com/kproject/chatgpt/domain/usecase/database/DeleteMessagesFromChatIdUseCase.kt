package com.kproject.chatgpt.domain.usecase.database

fun interface DeleteMessagesFromChatIdUseCase {
    suspend operator fun invoke(chatId: Long)
}