package dev.gaddal.scribbledash.gameModes.speedDraw.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.core.domain.util.formatted
import dev.gaddal.scribbledash.core.presentation.designsystem.CustomTextStyles.XSmall
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors.SurfaceLow
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.applyIf
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow

@Composable
fun TimerCircle(
    remainingTimeInSeconds: Int,
    isTimeLow: Boolean,
    onTimerComplete: () -> Unit = {}
) {
    // Format time as MM:SS
    val timeText = remember(remainingTimeInSeconds) {
        remainingTimeInSeconds.formatted()
    }

    // Text color based on remaining time
    val textColor = if (isTimeLow) {
        AppColors.ErrorRed
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    // Effect to handle timer completion
    LaunchedEffect(remainingTimeInSeconds) {
        if (remainingTimeInSeconds <= 0) {
            onTimerComplete()
        }
    }

    Box(
        modifier = Modifier
            .size(56.dp)
            .applyIf(isTimeLow) {
                Modifier.border(
                    width = 2.dp,
                    color = SurfaceLow,
                    shape = CircleShape
                )
            }
            .dropShadow(
                shape = CircleShape,
                blur = 24.dp,
                offsetY = 8.dp,
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = timeText,
                style = XSmall,
                color = textColor
            )
        }
    }
}