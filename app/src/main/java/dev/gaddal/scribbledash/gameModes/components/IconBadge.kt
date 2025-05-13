package dev.gaddal.scribbledash.gameModes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.CustomTextStyles.XSmall
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.applyIf
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow

@Composable
fun IconBadge(
    text: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    backgroundGradient: Brush? = null,
    textColor: Color,
    textStyle: TextStyle,
    iconSize: Dp = 36.dp,
    badgeHeight: Dp = 28.dp,
    minWidth: Dp = 60.dp,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    shape: Shape = RoundedCornerShape(20.dp),
    contentDescription: String? = null,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .height(badgeHeight)
                .padding(start = iconSize / 2)
                .widthIn(min = minWidth)
                .applyIf(borderWidth > 0.dp) {
                    Modifier.border(width = borderWidth, color = borderColor, shape = shape)
                }
                .applyIf(backgroundGradient != null) {
                    Modifier.background(brush = backgroundGradient!!, shape = shape)
                }
                .applyIf(backgroundGradient == null && backgroundColor != null) {
                    Modifier.background(color = backgroundColor!!, shape = shape)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(iconSize / 2 + 4.dp))
            Text(
                text = text,
                modifier = Modifier.padding(bottom = 2.dp),
                color = textColor,
                style = textStyle
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Image(
            painter = painterResource(iconResId),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.CenterStart),
        )
    }
}

@Composable
@Preview
fun IconBadgePreview() {
    ScribbleDashTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            // Example with solid background
            IconBadge(
                text = "5",
                iconResId = R.drawable.color_palette,
                backgroundColor = AppColors.SurfaceLow,
                textColor = MaterialTheme.colorScheme.onSurface,
                textStyle = XSmall,
                contentDescription = "Color Palette"
            )
        }
    }
}

@Composable
@Preview
fun IconBadgeWithGradientPreview() {
    ScribbleDashTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            // Example with gradient and border
            IconBadge(
                text = "New High Score",
                iconResId = R.drawable.high_score,
                modifier = Modifier
                    .dropShadow(
                        shape = RoundedCornerShape(20.dp),
                        blur = 24.dp,
                        offsetY = 8.dp,
                    ),
                backgroundGradient = Brush.linearGradient(AppColors.HighScoreGradient),
                textColor = MaterialTheme.colorScheme.surface,
                textStyle = XSmall,
                minWidth = 138.dp,
                borderWidth = 2.dp,
                borderColor = MaterialTheme.colorScheme.surface,
                contentDescription = "Star"
            )
        }
    }
}