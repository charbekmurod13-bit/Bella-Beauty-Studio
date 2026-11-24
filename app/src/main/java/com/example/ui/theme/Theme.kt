package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val BellaLightColorScheme = lightColorScheme(
    primary = BellaRose,
    onPrimary = BellaWhite,
    primaryContainer = BellaBlush,
    onPrimaryContainer = BellaInk,
    secondary = BellaGold,
    onSecondary = BellaWhite,
    secondaryContainer = BellaSand,
    onSecondaryContainer = BellaInk,
    tertiary = BellaMuted,
    onTertiary = BellaWhite,
    background = BellaIvory,
    onBackground = BellaInk,
    surface = BellaWhite,
    onSurface = BellaInk,
    surfaceVariant = BellaSand,
    onSurfaceVariant = BellaMuted,
    outline = BellaBorder,
    outlineVariant = BellaBorder
)

val BellaShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

object BellaSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val screenHorizontal = 20.dp
}

@Composable
fun BellaBeautyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BellaLightColorScheme,
        typography = BellaTypography,
        shapes = BellaShapes,
        content = content
    )
}
