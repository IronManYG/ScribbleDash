package dev.gaddal.scribbledash.gameModes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.CustomTextStyles.XSmall
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow

@Composable
fun NewHighScore(
    modifier: Modifier = Modifier,
) {
    IconBadge(
        text = stringResource(R.string.new_high_score),
        iconResId = R.drawable.high_score,
        modifier = modifier,
        backgroundGradient = Brush.linearGradient(AppColors.HighScoreGradient),
        textColor = MaterialTheme.colorScheme.surface,
        textStyle = XSmall,
        iconSize = 32.dp,
        badgeHeight = 34.dp,
        minWidth = 138.dp,
        borderWidth = 2.dp,
        borderColor = MaterialTheme.colorScheme.surface,
        contentDescription = stringResource(R.string.star)
    )
}

@Preview
@Composable
fun NewHighScorePreview() {
    ScribbleDashTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            NewHighScore(
                modifier = Modifier.dropShadow(
                    shape = RoundedCornerShape(20.dp),
                    blur = 24.dp,
                    offsetY = 8.dp,
                )
            )
        }
    }
}