package com.kproject.chatgpt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kproject.chatgpt.presentation.navigation.NavigationGraph
import com.kproject.chatgpt.presentation.screens.home.HomeViewModel
import com.kproject.chatgpt.presentation.theme.ChatGPTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val themeOption = homeViewModel.homeUiState.themeOption
            val isDarkMode = homeViewModel.homeUiState.isDarkMode
            ChatGPTTheme(themeOption = themeOption, isDarkMode = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationGraph(homeViewModel = homeViewModel)
                }
            }
        }
    }
}