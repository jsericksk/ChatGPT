package com.kproject.chatgpt.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kproject.chatgpt.commom.PrefsConstants
import com.kproject.chatgpt.domain.usecase.preferences.GetPreferenceAsyncUseCase
import com.kproject.chatgpt.domain.usecase.preferences.SavePreferenceUseCase
import com.kproject.chatgpt.presentation.model.fakeRecentChatsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"
private const val KEY = "kkk"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPreferenceAsyncUseCase: GetPreferenceAsyncUseCase,
    private val savePreferenceUseCase: SavePreferenceUseCase
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState(recentChatsList = fakeRecentChatsList))
        private set

    init {
        collectApiKey()
    }

    private fun collectApiKey() {
        viewModelScope.launch {
            getPreferenceAsyncUseCase(
                key = PrefsConstants.ApiKey,
                defaultValue = ""
            ).collectLatest {
                homeUiState = homeUiState.copy(apiKey = it as String)
            }
        }
    }

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch {
            savePreferenceUseCase(
                key = PrefsConstants.ApiKey,
                value = apiKey
            )
        }
    }
}