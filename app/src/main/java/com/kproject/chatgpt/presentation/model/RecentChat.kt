package com.kproject.chatgpt.presentation.model

data class RecentChat(
    val chatId: Long,
    val chatName: String,
    val usedTokens: Int,
    val lastMessage: String,
    val lastMessageDate: String,
    val lastMessageSentByUser: Boolean,
    val chatMode: Boolean
)

val fakeRecentChatsList = (0..20).map { index ->
    val chatName = if (index % 2 == 0) "Programming questions" else "Stupid questions"
    val lastMessage = if (index % 2 == 0) "What is Jetpack Compose?" else "How many kilograms does air weigh?"
    val lastMessageSentByUserAndChatMode = index % 2 == 0
    RecentChat(
        chatId = 1234,
        chatName = chatName,
        usedTokens = 1234,
        lastMessage = lastMessage,
        lastMessageDate = "12/12",
        lastMessageSentByUser = lastMessageSentByUserAndChatMode,
        chatMode = lastMessageSentByUserAndChatMode
    )
}