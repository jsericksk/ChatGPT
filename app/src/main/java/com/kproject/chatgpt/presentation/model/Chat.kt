package com.kproject.chatgpt.presentation.model

import java.util.Date

data class Chat(
    val message: String = "",
    val sentByUser: Boolean = true,
    val sendDate: Date? = null
)

val fakeChatList = listOf(
    Chat(
        message = "What is Jetpack Compose?",
        sentByUser = true,
        sendDate = null
    ),
    Chat(
        message = "Jetpack Compose is Android's recommended modern toolkit for building native UI. " +
                "It simplifies and accelerates UI development on Android.",
        sentByUser = false,
        sendDate = null
    ),
    Chat(
        message = "What is Jetpack Compose?",
        sentByUser = true,
        sendDate = null
    ),

)