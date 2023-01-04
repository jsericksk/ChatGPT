package com.kproject.chatgpt.domain.repository

interface PreferenceRepository {

    fun getPreference(key: String, defaultValue: Any): Any

    fun savePreference(key: String, value: Any)
}