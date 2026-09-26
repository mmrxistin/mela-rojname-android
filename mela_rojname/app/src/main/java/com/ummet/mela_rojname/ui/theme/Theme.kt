// Bismillahir Rahmanir Rahim
// Elhamdu Lillahi Rabbul Alemin
// Esselatu vesselamu ala rasulina Muhammedin
package com.ummet.mela_rojname.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Ochre,
    onPrimary = Coffee,
    primaryContainer = Chestnut,
    onPrimaryContainer = Cream,
    secondary = Gold,
    onSecondary = Coffee,
    secondaryContainer = Sand,
    onSecondaryContainer = Coffee,
    background = Coffee,
    onBackground = Cream,
    surface = Chestnut,
    onSurface = Cream,
    surfaceVariant = Chestnut,
    outline = Gold
)

private val LightColorScheme = lightColorScheme(
    primary = Ochre,
    onPrimary = Coffee,
    primaryContainer = Sand,
    onPrimaryContainer = Coffee,
    secondary = Gold,
    onSecondary = Coffee,
    secondaryContainer = Cream,
    onSecondaryContainer = Coffee,
    background = Cream,
    onBackground = Coffee,
    surface = Color(0xFFFDF8F0),
    onSurface = Coffee,
    surfaceVariant = Color(0xFFF0E6D5),
    outline = Olive
)

@Composable
fun Mela_rojnameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}