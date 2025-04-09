package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode.Companion.allGameModes
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.home.presentation.HomeAction


@Composable
fun GameModeSection(
    modifier: Modifier = Modifier,
    onAction: (HomeAction) -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleAndDescription(
            title = stringResource(R.string.start_drawing),
            description = stringResource(R.string.select_game_mode),
            modifier = Modifier.padding(top = 64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(allGameModes) { gameMode ->
                GameModeButton(
                    gameMode = gameMode,
                    backgroundColor = AppColors.SuccessGreen,
                    onClick = { gameMode -> onAction(HomeAction.OnGameModeClick(gameMode)) },
                    title = gameMode.name.asString(),
                    image = ImageVector.vectorResource(id = gameMode.imageResId)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameModeSectionPreview() {
    ScribbleDashTheme {
        GameModeSection(
            onAction = {}
        )
    }
}