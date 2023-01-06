package com.kproject.chatgpt.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kproject.chatgpt.commom.DataState
import com.kproject.chatgpt.commom.PrefsConstants
import com.kproject.chatgpt.commom.error.ApiResponseError
import com.kproject.chatgpt.data.api.ApiService
import com.kproject.chatgpt.data.api.entity.ErrorResponse
import com.kproject.chatgpt.data.api.entity.MessageBody
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.mapper.fromModel
import com.kproject.chatgpt.data.mapper.toModel
import com.kproject.chatgpt.domain.model.MessageModel
import com.kproject.chatgpt.domain.model.RecentChatModel
import com.kproject.chatgpt.domain.repository.MessageRepository
import com.kproject.chatgpt.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.ResponseBody
import java.util.*

class MessageRepositoryImpl(
    private val messageDao: MessageDao,
    private val apiService: ApiService,
    private val preferenceRepository: PreferenceRepository
) : MessageRepository {

    override fun getMessagesByChatId(chatId: Long): Flow<List<MessageModel>> {
        return messageDao.getMessagesByChatId(chatId).map { messageListEntity ->
            messageListEntity.map { messageEntity ->
                messageEntity.toModel()
            }
        }
    }

    override suspend fun addMessage(message: MessageModel) {
        messageDao.addMessage(message.fromModel())
    }

    override suspend fun sendMessage(
        message: String,
        recentChat: RecentChatModel
    ): DataState<MessageModel> {
        val aiModelOptions = recentChat.aiModelOptions
        val messageBody = MessageBody(
            model = aiModelOptions.aiModel.value,
            prompt = message,
            maxTokens = aiModelOptions.maxTokens,
            temperature = aiModelOptions.temperature
        )

        val apiKey = preferenceRepository.getPreference(PrefsConstants.ApiKey, "")
        try {
            val apiResponse = apiService.sendMessage(
                apiKey = "Bearer $apiKey",
                messageBody = messageBody
            )
            lateinit var messageModel: MessageModel
            if (apiResponse.code() == 200) {
                apiResponse.body()?.let { messageResponse ->
                    val answerText = messageResponse.choices.first().text.trim()
                    messageModel = MessageModel(
                        chatId = recentChat.chatId,
                        message = answerText,
                        sentByUser = false,
                        sendDate = Date(),
                        totalTokens = messageResponse.usage.totalTokens
                    )
                    messageDao.addMessage(messageModel.fromModel())
                }
                return DataState.Success(messageModel)
            } else {
                return handleApiError(apiResponse.errorBody())
            }
        } catch (e: Exception) {
            return DataState.Error(ApiResponseError.UnknownError)
        }
    }

    private fun handleApiError(errorBody: ResponseBody?): DataState<MessageModel> {
        if (errorBody != null) {
            val type = object : TypeToken<ErrorResponse>() {}.type
            val errorResponse: ErrorResponse? = Gson().fromJson(errorBody.charStream(), type)
            errorResponse?.let {
                val errorMessage = errorResponse.error.message
                return when {
                    errorMessage.contains("incorrect API key", ignoreCase = true) -> {
                        DataState.Error(ApiResponseError.InvalidApiKey)
                    }
                    errorMessage.contains("maximum context length", ignoreCase = true) -> {
                        DataState.Error(ApiResponseError.MaxTokensReached)
                    }
                    else -> {
                        DataState.Error(ApiResponseError.UnknownError)
                    }
                }
            }
        }
        return DataState.Error(ApiResponseError.UnknownError)
    }

    override suspend fun deleteMessagesFromChatId(chatId: Long) {
        messageDao.deleteMessagesFromChatId(chatId)
    }
}