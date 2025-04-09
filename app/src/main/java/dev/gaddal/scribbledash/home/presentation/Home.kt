@file:OptIn(ExperimentalMaterial3Api::class)

package dev.gaddal.scribbledash.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashScaffold
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashTopAppBar
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import dev.gaddal.scribbledash.core.presentation.ui.navigation.homeNavItemsInfo
import dev.gaddal.scribbledash.home.presentation.components.GameModeSection
import dev.gaddal.scribbledash.home.presentation.components.HomeBottomBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoot(
    onGameModeClick: (GameMode) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnGameModeClick -> onGameModeClick(action.gameMode)
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
                title = stringResource(R.string.app_name),
                showBackButton = false,
                showSettingsButton = false,
                titleTextStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        },
        bottomBar = {
            HomeBottomBar(
                state = state,
                onAction = onAction,
                navItems = homeNavItemsInfo,
                withLabel = false,
            )
        },
    ) { innerPadding ->

        when (state.currentDestination) {
            HomeNavItem.Chart -> {
                // Chart
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Chart",
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            }

            HomeNavItem.HomeGameMode -> {
                // Game Mode
                GameModeSection(
                    modifier = Modifier.padding(innerPadding),
                    onAction = onAction
                )
            }

            else -> {}
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