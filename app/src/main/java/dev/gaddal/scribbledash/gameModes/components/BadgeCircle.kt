package dev.gaddal.scribbledash.gameModes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow

@Composable
fun BadgeCircle(
    iconRes: Int,
    backgroundColor: Color,
    iconTint: Color = MaterialTheme.colorScheme.surface,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier
            .size(84.dp)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .dropShadow(
                shape = CircleShape,
                blur = 24.dp,
                offsetY = 8.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center),
                tint = iconTint
            )
        }
    }
}

@Preview
@Composable
fun BadgeCirclePreview() {
    ScribbleDashTheme {
        BadgeCircle(
            iconRes = R.drawable.check,
            backgroundColor = MaterialTheme.colorScheme.primary,
            iconTint = MaterialTheme.colorScheme.onPrimary
        )
    }
}