package com.kproject.chatgpt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kproject.chatgpt.presentation.navigation.NavigationGraph
import com.kproject.chatgpt.presentation.screens.chat.ChatScreen
import com.kproject.chatgpt.presentation.screens.home.HomeScreen
import com.kproject.chatgpt.presentation.theme.ChatGPTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatGPTTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // NavigationGraph()
                    ChatScreen(chatId = 1234) {

                    }
                }
            }
        }
    }
}