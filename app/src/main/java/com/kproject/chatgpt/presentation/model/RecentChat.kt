package com.kproject.chatgpt.presentation.model

import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.domain.model.RecentChatModel
import java.util.*

data class RecentChat(
    val chatId: Long = 0,
    val chatName: String = "",
    val usedTokens: Int = 0,
    val lastMessage: String = "",
    val lastMessageDate: Date = Date(),
    val lastMessageSentByUser: Boolean = true,
    val chatMode: Boolean = true,
    val aiModelOptions: AIModelOptions = AIModelOptions()
)

fun RecentChatModel.fromModel() = RecentChat(
    chatId = chatId,
    chatName = chatName,
    usedTokens = usedTokens,
    lastMessage = lastMessage,
    lastMessageDate = lastMessageDate,
    lastMessageSentByUser = lastMessageSentByUser,
    chatMode = chatMode,
    aiModelOptions = aiModelOptions
)

val fakeRecentChatsList = (0..20).map { index ->
    val chatName = if (index % 2 == 0) "Programming questions" else "Stupid questions"
    val lastMessage =
            if (index % 2 == 0) "What is Jetpack Compose?" else "How many kilograms does air weigh?"
    val lastMessageSentByUserAndChatMode = index % 2 == 0
    RecentChat(
        chatId = 1234,
        chatName = chatName,
        usedTokens = 1234,
        lastMessage = lastMessage,
        lastMessageDate = Date(),
        lastMessageSentByUser = lastMessageSentByUserAndChatMode,
        chatMode = lastMessageSentByUserAndChatMode
    )
}