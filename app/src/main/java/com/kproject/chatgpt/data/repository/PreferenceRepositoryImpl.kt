package com.kproject.chatgpt.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kproject.chatgpt.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore("settings")

class PreferenceRepositoryImpl(private val applicationContext: Context) : PreferenceRepository {

    override suspend fun getPreferenceAsync(key: String, defaultValue: Any): Flow<Any> {
        val keyPreferences = when (defaultValue) {
            is String -> stringPreferencesKey(key)
            is Int -> intPreferencesKey(key)
            is Boolean -> booleanPreferencesKey(key)
            else -> throw Exception()
        }
        return applicationContext.dataStore.data.map { preferences ->
            preferences[keyPreferences] ?: defaultValue
        }
    }

    override fun getPreferenceSync(key: String, defaultValue: Any): Any {
        val keyPreferences = when (defaultValue) {
            is String -> stringPreferencesKey(key)
            is Int -> intPreferencesKey(key)
            is Boolean -> booleanPreferencesKey(key)
            else -> throw Exception()
        }
        return runBlocking {
            applicationContext.dataStore.data.map { preferences ->
                preferences[keyPreferences] ?: defaultValue
            }.first()
        }
    }

    override suspend fun savePreference(key: String, value: Any) {
        when (value) {
            is String -> {
                val keyPreferences = stringPreferencesKey(key)
                applicationContext.dataStore.edit {
                    it[keyPreferences] = value
                }
            }
            is Int -> {
                val keyPreferences = intPreferencesKey(key)
                applicationContext.dataStore.edit {
                    it[keyPreferences] = value
                }
            }
            is Boolean -> {
                val keyPreferences = booleanPreferencesKey(key)
                applicationContext.dataStore.edit {
                    it[keyPreferences] = value
                }
            }
            else -> throw Exception()
        }
    }
}