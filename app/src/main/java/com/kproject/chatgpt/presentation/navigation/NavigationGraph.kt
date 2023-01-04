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
import com.kproject.chatgpt.presentation.extensions.toJson
import com.kproject.chatgpt.presentation.screens.chat.ChatScreen
import com.kproject.chatgpt.presentation.screens.home.HomeScreen
import com.kproject.chatgpt.presentation.screens.home.HomeViewModel

const val ArgChatArgs = "chatArgs"

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(homeViewModel: HomeViewModel) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                homeViewModel = homeViewModel,
                onNavigateToChatScreen = { chatArgs ->
                    navController.navigate(
                        Screen.ChatScreen.route + "/${chatArgs.toJson()}"
                    )
                }
            )
        }

        // ChatScreen
        composable(
            route = Screen.ChatScreen.route + "/{$ArgChatArgs}",
            arguments = listOf(
                navArgument(name = ArgChatArgs) {
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
            ChatScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}