package com.kproject.chatgpt.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kproject.chatgpt.presentation.screens.chat.ChatScreen
import com.kproject.chatgpt.presentation.screens.home.HomeScreen

const val NullChatId = -1L

const val ArgChatId = "chatId"
const val ArgApiKey = "apiKey"
const val ArgConversationModeKey = "apiKey"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToChatScreen = { chatId, apiKey, conversationMode ->
                    navController.navigate(Screen.ChatScreen.route + "/$chatId/$apiKey")
                }
            )
        }

        // ChatScreen
        composable(
            route = Screen.ChatScreen.route + "/{$ArgChatId}/{$ArgApiKey}",
            arguments = listOf(
                navArgument(name = ArgChatId) {
                    type = NavType.LongType
                },
                navArgument(name = ArgApiKey) {
                    type = NavType.StringType
                },
                navArgument(name = ArgConversationModeKey) {
                    type = NavType.IntType
                },
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            }
        ) { navBackStackEntry ->
            ChatScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}