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

    override fun <T> getPreference(key: String, defaultValue: T): T {
        return getValue(key, defaultValue)
    }

    override fun <T> savePreference(key: String, value: T) {
        saveValue(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> prefs.getString(key, defaultValue as String) as T
            is Boolean -> prefs.getBoolean(key, defaultValue as Boolean) as T
            is Int -> prefs.getInt(key, defaultValue as Int) as T
            is Long -> prefs.getLong(key, defaultValue as Long) as T
            is Float -> prefs.getFloat(key, defaultValue as Float) as T
            else -> throw UnsupportedOperationException("Type provided as defaultValue not supported")
        }
    }

    private fun <T> saveValue(key: String, value: T) {
        when (value) {
            is String -> prefs.edit { putString(key, value) }
            is Boolean -> prefs.edit { putBoolean(key, value) }
            is Int -> prefs.edit { putInt(key, value) }
            is Long -> prefs.edit { putLong(key, value) }
            is Float -> prefs.edit { putFloat(key, value) }
            else -> throw UnsupportedOperationException("Type provided as defaultValue not supported")
        }
    }
}