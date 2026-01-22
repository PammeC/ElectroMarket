package com.example.defructas.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ElectroBlue,
    secondary = ElectroOrange,
    background = ElectroBackground,
    surface = ElectroSurface,
    onPrimary = ElectroSurface,
    onSecondary = ElectroSurface,
    onBackground = ElectroText,
    onSurface = ElectroText
)

private val LightColorScheme = lightColorScheme(
    primary = ElectroBlue,
    secondary = ElectroOrange,
    background = ElectroBackground,
    surface = ElectroSurface,
    onPrimary = ElectroSurface,
    onSecondary = ElectroSurface,
    onBackground = ElectroText,
    onSurface = ElectroText
)

@Composable
fun DeFructasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // 🚫 MUY IMPORTANTE
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
