package com.kproject.chatgpt.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kproject.chatgpt.presentation.theme.custom.ThemeOptions

@Composable
fun ChatGPTTheme(
    themeOption: Int = ThemeOptions.DefaultTheme,
    isDarkMode: Boolean,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkMode) {
        ThemeOptions.darkThemeOptions[themeOption]
    } else {
        ThemeOptions.lightThemeOptions[themeOption]
    }
    val themeColors = darkColors(
        primary = theme.primary,
        primaryVariant = theme.primaryVariant,
        onPrimary = theme.onPrimary,
        secondary = theme.secondary,
        onSecondary = theme.onSecondary,
        background = theme.background,
        surface = theme.surface,
        onSurface = theme.onSurface
    )
    MaterialTheme(
        colors = themeColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = themeColors.primaryVariant
        )
    }
}

@Composable
fun PreviewTheme(
    themeOption: Int = ThemeOptions.DefaultTheme,
    isDarkMode: Boolean = true,
    content: @Composable () -> Unit
) {
    val theme = if (isDarkMode) {
        ThemeOptions.darkThemeOptions[themeOption]
    } else {
        ThemeOptions.lightThemeOptions[themeOption]
    }
    val themeColors = darkColors(
        primary = theme.primary,
        onPrimary = theme.onPrimary,
        secondary = theme.secondary,
        onSecondary = theme.onSecondary,
        background = theme.background,
        surface = theme.surface,
        onSurface = theme.onSurface
    )
    MaterialTheme(
        colors = themeColors,
        typography = Typography,
        shapes = Shapes,
        content = {
            Surface(color = MaterialTheme.colors.background) {
                content()
            }
        }
    )
}