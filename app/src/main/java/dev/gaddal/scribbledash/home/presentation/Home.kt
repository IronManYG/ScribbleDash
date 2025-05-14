@file:OptIn(ExperimentalMaterial3Api::class)

package dev.gaddal.scribbledash.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.designsystem.CustomTextStyles.LabelExtraLarge
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashScaffold
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashTopAppBar
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import dev.gaddal.scribbledash.core.presentation.ui.navigation.homeNavItemsInfo
import dev.gaddal.scribbledash.home.presentation.components.ChartSection
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
                title = when (state.currentDestination) {
                    is HomeNavItem.Chart -> stringResource(R.string.statistics)
                    else -> stringResource(R.string.app_name)
                },
                showBackButton = false,
                showSettingsButton = false,
                titleTextStyle = when (state.currentDestination) {
                    is HomeNavItem.Chart -> LabelExtraLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    else -> MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
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
        val animationDuration = 300

        val slideInAnimation = slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = animationDuration)
        ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))

        val slideOutAnimation = slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = animationDuration)
        ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))

        Column(modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(
                visible = state.currentDestination == HomeNavItem.Chart,
                enter = slideInAnimation,
                exit = slideOutAnimation
            ) {
                // Chart
                ChartSection(
                    state = state,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                )
            }

            AnimatedVisibility(
                visible = state.currentDestination == HomeNavItem.HomeGameMode,
                enter = slideInAnimation,
                exit = slideOutAnimation
            ) {
                // Game Mode
                GameModeSection(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    onAction = onAction
                )
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