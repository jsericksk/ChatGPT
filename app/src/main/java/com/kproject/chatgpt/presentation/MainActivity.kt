package com.kproject.chatgpt.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.kproject.chatgpt.presentation.model.AIModelOptions
import com.kproject.chatgpt.presentation.navigation.NavigationGraph
import com.kproject.chatgpt.presentation.screens.chat.components.AIModelOptionsAlertDialog
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
                    NavigationGraph()
                    // ChatScreen(chatId = 1234) {}
                    /**AIModelOptionsAlertDialog(
                        showDialog = true,
                        aiModelOptions = AIModelOptions(),
                        onDismiss = {}
                    )*/
                }
            }
        }
    }
}