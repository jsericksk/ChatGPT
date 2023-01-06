package com.kproject.chatgpt.presentation.theme.custom

import androidx.compose.ui.graphics.Color

private val defaultOnPrimaryColor = Color(0xFF1D1D1D)
private val defaultBackgroundColor = Color(0xFFF0F0F0)
private val defaultSurfaceColor = Color(0xFF4B4B4B)
private val defaultOnSurfaceColor = Color(0xFFECECEC)

data class ThemeLightOption1(
    override val primary: Color = Color(0xFF013477),
    override val primaryVariant: Color = Color(0xFF022E66),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF064496),
    override val onSecondary: Color = Color(0xFF0C3D7C),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeLightOption2(
    override val primary: Color = Color(0xFF8A0707),
    override val primaryVariant: Color = Color(0xFF800606),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF9B0A0A),
    override val onSecondary: Color = Color(0xFF9C1515),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeLightOption3(
    override val primary: Color = Color(0xFF025302),
    override val primaryVariant: Color = Color(0xFF073D07),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF0C5A0C),
    override val onSecondary: Color = Color(0xFF115211),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeLightOption4(
    override val primary: Color = Color(0xFF460D8D),
    override val primaryVariant: Color = Color(0xFF3E1174),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF501C91),
    override val onSecondary: Color = Color(0xFF471485),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeLightOption5(
    override val primary: Color = Color(0xFFCE780C),
    override val primaryVariant: Color = Color(0xFFC46F07),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFFE98A12),
    override val onSecondary: Color = Color(0xFFC47C21),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors

data class ThemeLightOption6(
    override val primary: Color = Color(0xFF384952),
    override val primaryVariant: Color = Color(0xFF38454D),
    override val onPrimary: Color = defaultOnPrimaryColor,
    override val secondary: Color = Color(0xFF3E535E),
    override val onSecondary: Color = Color(0xFF3B4C55),
    override val background: Color = defaultBackgroundColor,
    override val surface: Color = defaultSurfaceColor,
    override val onSurface: Color = defaultOnSurfaceColor
) : ThemeColors