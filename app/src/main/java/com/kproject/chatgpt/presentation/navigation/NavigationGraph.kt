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
import com.kproject.chatgpt.presentation.screens.home.HomeScreen

private const val ArgChatId = "chatId"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                onNavigateToChatScreen = {

                }
            )
        }

        // ChatScreen
        composable(
            route = Screen.ChatScreen.route + "/{$ArgChatId}",
            arguments = listOf(
                navArgument(name = ArgChatId) {
                    type = NavType.StringType
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
            /**ChatScreen(
                chatId = navBackStackEntry.arguments!!.getLong(ArgChatId),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )*/
        }
    }
}