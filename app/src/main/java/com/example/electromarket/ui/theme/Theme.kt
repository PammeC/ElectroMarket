package com.example.electromarket.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ElectroSecondaryCyan,      // En dark mode, el celeste resalta mejor
    secondary = ElectroPrimaryBlue,
    background = Color(0xFF0F172A),      // Fondo oscuro azulado
    surface = Color(0xFF1E293B),         // Superficie oscura
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFFE2E8F0),
    onSurface = Color(0xFFE2E8F0)
)

private val LightColorScheme = lightColorScheme(
    primary = ElectroPrimaryBlue,
    secondary = ElectroSecondaryCyan,
    background = ElectroLightBackground,
    surface = ElectroSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = ElectroTextPrimary,
    onSurface = ElectroTextPrimary
)

@Composable
fun ElectroMarketTheme(
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
