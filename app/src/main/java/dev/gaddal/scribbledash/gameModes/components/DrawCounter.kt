package dev.gaddal.scribbledash.gameModes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.CustomTextStyles.XSmall
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors.SurfaceLow

@Composable
fun DrawCounter(
    modifier: Modifier = Modifier,
    count: String,
    isNewHigh: Boolean = false,
) {
    val actualBackgroundColor = if (isNewHigh) {
        Color(0xFFED6363)
    } else {
        SurfaceLow
    }

    val actualTextColor = if (isNewHigh) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    val actualImageResId = if (isNewHigh) {
        R.drawable.color_palette_outlined
    } else {
        R.drawable.color_palette
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconBadge(
            text = count,
            iconResId = actualImageResId,
            backgroundColor = actualBackgroundColor,
            textColor = actualTextColor,
            textStyle = XSmall,
            contentDescription = "Color Palette"
        )
        AnimatedVisibility(isNewHigh) {
            Text(
                text = stringResource(R.string.new_high),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
fun DrawCounterPreview() {
    ScribbleDashTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            DrawCounter(
                count = "5",
                isNewHigh = true,
            )
        }
    }
}