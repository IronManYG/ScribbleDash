package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashButton

@Composable
fun GameModeButton(
    modifier: Modifier = Modifier,
    gameMode: GameMode,
    backgroundColor: Color,
    onClick: (GameMode) -> Unit,
    title: String,
    image: Painter
) {
    ScribbleDashButton(
        modifier = modifier,
        onClick = { onClick(gameMode) },
        backgroundColor = backgroundColor,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentPadding = PaddingValues(0.dp),
        title = title
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
                painter = image,
                contentDescription = null,
                alignment = Alignment.CenterEnd,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameModeButtonPreview() {
    ScribbleDashTheme {
        // Assuming the first game mode for preview
        val gameMode = GameMode.allGameModes.firstOrNull() ?: return@ScribbleDashTheme
        GameModeButton(
            gameMode = gameMode,
            backgroundColor = AppColors.SuccessGreen,
            onClick = {},
            title = gameMode.name.asString(),
            image = painterResource(id = R.drawable.one_round_wonder)
        )
    }
}