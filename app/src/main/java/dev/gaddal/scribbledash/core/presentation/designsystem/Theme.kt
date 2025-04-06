package dev.gaddal.scribbledash.core.presentation.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.ColorScheme

@Composable
fun ScribbleDashTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}