@file:OptIn(ExperimentalMaterial3Api::class)

package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.domain.gameMode.Level
import dev.gaddal.scribbledash.core.presentation.designsystem.AppIcons
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashScaffold
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashTopAppBar
import dev.gaddal.scribbledash.drawingCanvas.data.DefaultCanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components.CanvasDrawingSection
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components.DifficultyLevel
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components.ResultScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun OneRoundWonderRoot(
    onCloseClick: () -> Unit,
    onLevelClick: () -> Unit,
    viewModel: OneRoundWonderViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OneRoundWonderScreen(
        state = state,
        canvasController = viewModel.canvasManager,
        onAction = { action ->
            when (action) {
                is OneRoundWonderAction.OnCloseClick -> onCloseClick()
                is OneRoundWonderAction.OnLevelClick -> onLevelClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun OneRoundWonderScreen(
    state: OneRoundWonderState,
    canvasController: CanvasController,
    onAction: (OneRoundWonderAction) -> Unit,
) {
    val animationDuration = 300

    ScribbleDashScaffold(
        withGradient = false,
        topAppBar = {
            ScribbleDashTopAppBar(
                title = "",
                showBackButton = false,
                showSettingsButton = false,
                endContent = {
                    IconButton(
                        onClick = {
                            onAction(OneRoundWonderAction.OnCloseClick)
                            canvasController.clearCanvas()
                        }
                    ) {
                        Icon(
                            imageVector = AppIcons.CloseCircle,
                            contentDescription = stringResource(id = R.string.close),
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                titleTextStyle = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
        },
    ) { innerPadding ->
        val slideInAnimation = slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = animationDuration)
        ) + fadeIn(animationSpec = tween(durationMillis = animationDuration))

        val slideOutAnimation = slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(durationMillis = animationDuration)
        ) + fadeOut(animationSpec = tween(durationMillis = animationDuration))

        val strokeWidth = when (state.level) {
            Level.Beginner -> 15f
            Level.Challenging -> 7f
            Level.Master -> 4f
            null -> 15f
        }

        AnimatedVisibility(
            visible = state.level == null,
            enter = slideInAnimation,
            exit = slideOutAnimation
        ) {
            DifficultyLevel(
                onLevelClick = { onAction(OneRoundWonderAction.OnLevelClick(it)) },
                modifier = Modifier.padding(innerPadding)
            )
        }

        AnimatedVisibility(
            visible = state.level != null,
            enter = slideInAnimation,
            exit = slideOutAnimation
        ) {
            AnimatedVisibility(
                visible = !state.showResult,
                enter = slideInAnimation,
                exit = slideOutAnimation
            ) {
                CanvasDrawingSection(
                    canvasController = canvasController,
                    modifier = Modifier.padding(innerPadding),
                    referenceDrawingResId = state.referenceResId,
                    strokeWidth = strokeWidth,
                    onDoneClick = { onAction(OneRoundWonderAction.OnDoneClick) }
                )
            }

            AnimatedVisibility(
                visible = state.showResult,
                enter = slideInAnimation,
                exit = slideOutAnimation
            ) {
                ResultScreen(
                    score = state.score,
                    referenceResId = state.referenceResId,
                    canvasController = canvasController,
                    innerPadding = innerPadding,
                    strokeWidth = strokeWidth,
                    onTryAgainClick = {
                        onAction(OneRoundWonderAction.OnTryAgainClick)
                        canvasController.clearCanvas()
                    }
                )
            }
        }
    }
}


@Preview
@Composable
private fun OneRoundWonderScreenPreview() {
    ScribbleDashTheme {
        OneRoundWonderScreen(
            state = OneRoundWonderState(
                level = Level.Beginner
            ),
            canvasController = DefaultCanvasController(),
            onAction = {}
        )
    }
}

