package com.kproject.chatgpt.data.api

import com.kproject.chatgpt.data.api.entity.MessageBody
import com.kproject.chatgpt.data.api.entity.MessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("completions")
    suspend fun sendMessage(
        @Header("Authorization") apiKey: String,
        @Body messageBody: MessageBody
    ): Response<MessageResponse>

    companion object {
        const val API_BASE_URL = "https://api.openai.com/v1/"
    }
}