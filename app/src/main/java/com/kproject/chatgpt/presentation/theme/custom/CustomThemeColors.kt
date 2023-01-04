package com.kproject.chatgpt.presentation.theme.custom

import androidx.compose.ui.graphics.Color

data class ThemeOption1(
    override val primary: Color = Color(0xFF002F6C),
    override val primaryVariant: Color = Color(0xFF012655),
    override val onPrimary: Color = Color(0xFFE4E4E4),
    override val secondary: Color = Color(0xFF003B88),
    override val onSecondary: Color = Color(0xFF003375),
    override val background: Color = Color(0xFF1F1F1F),
    override val surface: Color = Color(0xFF2B2A2A),
    override val onSurface: Color = Color(0xFFF3F3F3)
) : ThemeColors

data class ThemeOption2(
    override val primary: Color = Color(0xFF740101),
    override val primaryVariant: Color = Color(0xFF5C0000),
    override val onPrimary: Color = Color(0xFFE4E4E4),
    override val secondary: Color = Color(0xFF910303),
    override val onSecondary: Color = Color(0xFFAC0A0A),
    override val background: Color = Color(0xFF1F1F1F),
    override val surface: Color = Color(0xFF2B2A2A),
    override val onSurface: Color = Color(0xFFF3F3F3)
) : ThemeColors

data class ThemeOption3(
    override val primary: Color = Color(0xFF003300),
    override val primaryVariant: Color = Color(0xFF032703),
    override val onPrimary: Color = Color(0xFFE4E4E4),
    override val secondary: Color = Color(0xFF095209),
    override val onSecondary: Color = Color(0xFF0C500C),
    override val background: Color = Color(0xFF1F1F1F),
    override val surface: Color = Color(0xFF2B2A2A),
    override val onSurface: Color = Color(0xFFF3F3F3)
) : ThemeColors

data class ThemeOption4(
    override val primary: Color = Color(0xFF4A148C),
    override val primaryVariant: Color = Color(0xFF3D1370),
    override val onPrimary: Color = Color(0xFFE4E4E4),
    override val secondary: Color = Color(0xFF461D79),
    override val onSecondary: Color = Color(0xFF44177A),
    override val background: Color = Color(0xFF1F1F1F),
    override val surface: Color = Color(0xFF2B2A2A),
    override val onSurface: Color = Color(0xFFF3F3F3)
) : ThemeColors