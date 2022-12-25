package com.kproject.chatgpt.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object ChatScreen : Screen("chat_screen")
}