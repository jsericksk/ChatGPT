package com.kproject.chatgpt.data.mapper

import com.kproject.chatgpt.data.database.entity.MessageEntity
import com.kproject.chatgpt.data.database.entity.RecentChatEntity
import com.kproject.chatgpt.domain.model.MessageModel
import com.kproject.chatgpt.domain.model.RecentChatModel

fun RecentChatModel.fromModel() = RecentChatEntity(
    chatId = chatId,
    chatName = chatName,
    usedTokens = usedTokens,
    lastMessage = lastMessage,
    lastMessageDate = lastMessageDate,
    lastMessageSentByUser = lastMessageSentByUser,
    chatMode = chatMode,
    aiModelOptions = aiModelOptions
)

fun RecentChatEntity.toModel() = RecentChatModel(
    chatId = chatId,
    chatName = chatName,
    usedTokens = usedTokens,
    lastMessage = lastMessage,
    lastMessageDate = lastMessageDate,
    lastMessageSentByUser = lastMessageSentByUser,
    chatMode = chatMode,
    aiModelOptions = aiModelOptions
)

fun MessageModel.fromModel() = MessageEntity(
    id = id,
    chatId = chatId,
    message = message,
    sentByUser = sentByUser,
    sendDate = sendDate
)

fun MessageEntity.toModel() = MessageModel(
    id = id,
    chatId = chatId,
    message = message,
    sentByUser = sentByUser,
    sendDate = sendDate
)