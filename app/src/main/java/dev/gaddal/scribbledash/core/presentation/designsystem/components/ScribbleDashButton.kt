package dev.gaddal.scribbledash.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow

@Composable
fun ScribbleDashButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    containerColor: Color = AppColors.SuccessGreen,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledContainerColor: Color = AppColors.SurfaceLowest,
    disabledContentColor: Color = AppColors.Surface80,
    contentPadding: PaddingValues = PaddingValues(
        start = 24.dp,
        end = 24.dp,
        top = 12.dp,
        bottom = 12.dp
    ),
    title: String,
    content: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .dropShadow(
                shape = RoundedCornerShape(20.dp),
                blur = 24.dp,
                offsetY = 8.dp,
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            ),
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .padding(6.dp)
                .then(modifier),
            enabled = enabled,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = containerColor,
                contentColor = contentColor,
                disabledContainerColor = disabledContainerColor,
                disabledContentColor = disabledContentColor
            ),
            contentPadding = contentPadding,
        ) {
            if (content == null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
            } else {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScribbleDashButtonPreview() {
    ScribbleDashTheme {
        ScribbleDashButton(
            onClick = {},
            modifier = Modifier,
            title = "CLEAR CANVAS"
        )
    }
}