package ru.akella.cryptocoin.android.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    onPrimary = Color.White,
    primaryVariant = Purple700,
    secondary = Teal200,
    onSecondary = Color.White,
    background = Purple700,
    onBackground = Purple500,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    onPrimary = Color.White,
    primaryVariant = Purple700,
    secondary = Teal200,
    onSecondary = Color.White,
    background = Purple700,
    onBackground = Purple500,
    onSurface = Color.White,
    // Other default colors to override
    //
    // surface = Color.White,
    // onPrimary = Color.White,
    // onSecondary = Color.Black,
    // onSurface = Color.Black,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
