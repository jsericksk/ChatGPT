package com.kproject.chatgpt.domain.usecase.database

import com.kproject.chatgpt.domain.model.MessageModel

fun interface AddMessageUseCase {
    suspend operator fun invoke(message: MessageModel)
}