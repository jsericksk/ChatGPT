package com.kproject.chatgpt.presentation.model

import java.util.Date

data class Message(
    val message: String = "",
    val sentByUser: Boolean = true,
    val sendDate: Date? = null
)

val fakeChatList = listOf(
    Message(
        message = "What is Jetpack Compose?",
        sentByUser = true,
        sendDate = Date()
    ),
    Message(
        message = "Jetpack Compose is Android's recommended modern toolkit for building native UI. " +
                "It simplifies and accelerates UI development on Android.",
        sentByUser = false,
        sendDate = Date()
    ),
    Message(
        message = "What is the main difference between XML and Jetpack Compose?",
        sentByUser = true,
        sendDate = Date()
    ),
    Message(
        message = "The main difference between XML and Jetpack Compose is that XML is a markup " +
                "language used to create user interface layouts, while Jetpack Compose is a " +
                "declarative UI framework for Android that allows developers to create UI elements" +
                " with the help of Kotlin code. XML is a more traditional approach to creating user" +
                " interfaces, while Jetpack Compose is aimed at making the development" +
                " process easier and faster.",
        sentByUser = false,
        sendDate = Date()
    )
)