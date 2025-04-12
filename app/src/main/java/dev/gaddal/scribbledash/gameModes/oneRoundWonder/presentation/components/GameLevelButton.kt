package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.core.domain.gameMode.Level
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors

@Composable
fun GameLevelButton(
    level: Level,
    onClick: (Level) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            8.dp, alignment = Alignment.CenterVertically
        ),
        modifier = modifier
    ) {
        Button(
            onClick = { onClick(level) },
            modifier = Modifier.size(88.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 32.dp,
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = level.icon),
                contentDescription = level.title.asString(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = level.title.asString(),
            style = MaterialTheme.typography.labelMedium,
            color = AppColors.OnBackgroundVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GameLevelButtonPreview() {
    ScribbleDashTheme {
        GameLevelButton(
            level = Level.Beginner,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
