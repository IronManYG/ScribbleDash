package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.Level
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.home.presentation.components.TitleAndDescription

@Composable
fun DifficultyLevel(
    onLevelClick: (Level) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // title and description
        TitleAndDescription(
            title = stringResource(R.string.start_drawing),
            description = stringResource(R.string.choose_difficulty),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
        )
        // level buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            GameLevelButton(
                level = Level.Beginner,
                onClick = onLevelClick,
                modifier = Modifier.padding(top = 16.dp),
            )
            GameLevelButton(
                level = Level.Challenging,
                onClick = onLevelClick
            )
            GameLevelButton(
                level = Level.Master,
                onClick = onLevelClick,
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DifficultyLevelPreview() {
    ScribbleDashTheme {
        DifficultyLevel(
            onLevelClick = {}
        )
    }
}
