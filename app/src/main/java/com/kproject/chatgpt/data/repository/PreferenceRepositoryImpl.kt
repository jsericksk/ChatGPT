package com.kproject.chatgpt.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.kproject.chatgpt.domain.repository.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext

private const val PrefsName = "options"

class PreferenceRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val prefs: SharedPreferences = context.getSharedPreferences(PrefsName, 0)
) : PreferenceRepository {

    override fun getPreference(key: String, defaultValue: Any): Any {
        return prefs.getValue(key, defaultValue)
    }

    override fun savePreference(key: String, value: Any) {
        prefs.saveValue(key, value)
    }

    private fun SharedPreferences.getValue(key: String, defaultValue: Any): Any {
        return when (defaultValue) {
            is String -> getString(key, defaultValue) as Any
            is Boolean -> getBoolean(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> throw UnsupportedOperationException("Type provided as defaultValue not supported")
        }
    }

    private fun SharedPreferences.saveValue(key: String, value: Any) {
        when (value) {
            is String -> edit { putString(key, value) }
            is Boolean -> edit { putBoolean(key, value) }
            is Int -> edit { putInt(key, value) }
            is Long -> edit { putLong(key, value) }
            is Float -> edit { putFloat(key, value) }
            else -> throw UnsupportedOperationException("Type provided as defaultValue not supported")
        }
    }
}