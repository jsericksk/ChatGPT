package com.kproject.chatgpt.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {

    suspend fun getPreferenceAsync(key: String, defaultValue: Any): Flow<Any>

    fun getPreferenceSync(key: String, defaultValue: Any): Any

    suspend fun savePreference(key: String, value: Any)
}