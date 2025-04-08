@file:OptIn(ExperimentalMaterial3Api::class)

package dev.gaddal.scribbledash.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode.Companion.allGameModes
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashScaffold
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashTopAppBar
import dev.gaddal.scribbledash.core.presentation.ui.navigation.homeNavItemsInfo
import dev.gaddal.scribbledash.home.presentation.components.GameModeButton
import dev.gaddal.scribbledash.home.presentation.components.HomeBottomBar
import dev.gaddal.scribbledash.home.presentation.components.TitleAndDescription
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    onHomeClick: () -> Unit,
    onChartClick: () -> Unit,
    onGameModeClick: (GameMode) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnGameModeClick -> onGameModeClick(action.gameMode)
                is HomeAction.OnHomeClick -> onHomeClick()
                is HomeAction.OnChartClick -> onChartClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    ScribbleDashScaffold(
        topAppBar = {
            ScribbleDashTopAppBar(
                title = "ScribbleDash",
                showBackButton = false,
                showSettingsButton = false,
                titleTextStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        },
        bottomBar = {
            HomeBottomBar(
                onAction = onAction,
                navItems = homeNavItemsInfo,
                withLabel = false,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TitleAndDescription(
                title = stringResource(R.string.start_drawing),
                description = stringResource(R.string.select_game_mode),
                modifier = Modifier.padding(top = 64.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Game Modes
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
}

@Preview
@Composable
private fun Preview() {
    ScribbleDashTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}