package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors

@Composable
fun GameModeButton(
    gameMode: GameMode,
    backgroundColor: Color,
    onClick: (GameMode) -> Unit,
    title: String,
    image: ImageVector
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            ),
    ) {
        Button(
            onClick = { onClick(gameMode) },
            modifier = Modifier.padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(start = 22.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.headlineMedium
                )
                Image(
                    imageVector = image,
                    contentDescription = null,
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameModeButtonPreview() {
    ScribbleDashTheme {
        // Assuming the first game mode for preview
        val gameMode = GameMode.Companion.allGameModes.firstOrNull() ?: return@ScribbleDashTheme
        GameModeButton(
            gameMode = gameMode,
            backgroundColor = AppColors.SuccessGreen,
            onClick = {},
            title = gameMode.name.asString(),
            image = ImageVector.vectorResource(id = gameMode.imageResId)
        )
    }
}