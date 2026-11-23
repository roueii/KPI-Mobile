package com.example.kpiwonosari.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = Primary500,
    onPrimary = White,
    secondary = Secondary500,
    onSecondary = White,
    background = Neutral50,
    onBackground = Neutral800,
    surface = White,
    onSurface = Neutral800,
    error = Error600,
    outline = Neutral300,
)

@Composable
fun KPIWONOSARITheme(content: @Composable () -> Unit) {
    // The design is light-only, so the brand palette is always used.
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content,
    )
}
