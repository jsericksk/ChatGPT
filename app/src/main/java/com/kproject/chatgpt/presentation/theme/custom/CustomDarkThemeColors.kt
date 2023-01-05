package com.kproject.chatgpt.presentation.theme.custom

import androidx.compose.ui.graphics.Color

private val defaultOnPrimaryColor = Color(0xFFE4E4E4)
private val defaultBackgroundColor = Color(0xFF1F1F1F)
private val defaultSurfaceColor = Color(0xFF2C2B2B)
private val defaultOnSurfaceColor = Color(0xFFF3F3F3)

data class ThemeDarkOption1(
    override val primary: Color = Color(0xFF002F6C),
    override val primaryVariant: Color = Color(0xFF012655),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF003B88),
    override val onSecondary: Color = Color(0xFF003375),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeDarkOption2(
    override val primary: Color = Color(0xFF740202),
    override val primaryVariant: Color = Color(0xFF5C0000),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF910303),
    override val onSecondary: Color = Color(0xFFAC0A0A),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeDarkOption3(
    override val primary: Color = Color(0xFF003300),
    override val primaryVariant: Color = Color(0xFF032703),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF095209),
    override val onSecondary: Color = Color(0xFF0C500C),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeDarkOption4(
    override val primary: Color = Color(0xFF431280),
    override val primaryVariant: Color = Color(0xFF3D1370),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF461D79),
    override val onSecondary: Color = Color(0xFF44177A),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeDarkOption5(
    override val primary: Color = Color(0xFFCC7303),
    override val primaryVariant: Color = Color(0xFFB36402),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFFE98A12),
    override val onSecondary: Color = Color(0xFFC47C21),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeDarkOption6(
    override val primary: Color = Color(0xFF2D3A41),
    override val primaryVariant: Color = Color(0xFF2C363C),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF324149),
    override val onSecondary: Color = Color(0xFF354249),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors