@file:OptIn(ExperimentalMaterial3Api::class)

package dev.gaddal.scribbledash.gameModes.speedDraw.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import dev.gaddal.scribbledash.gameModes.components.CanvasDrawingSection
import dev.gaddal.scribbledash.gameModes.components.DifficultyLevel
import dev.gaddal.scribbledash.gameModes.components.DrawCounter
import dev.gaddal.scribbledash.gameModes.components.HighScoreScreen
import dev.gaddal.scribbledash.gameModes.speedDraw.presentation.components.TimerCircle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpeedDrawRoot(
    onCloseClick: () -> Unit,
    viewModel: SpeedDrawViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SpeedDrawScreen(
        state = state,
        canvasController = viewModel.canvasManager,
        onAction = { action ->
            when (action) {
                is SpeedDrawAction.OnCloseClick -> onCloseClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun SpeedDrawScreen(
    state: SpeedDrawState,
    canvasController: CanvasController,
    onAction: (SpeedDrawAction) -> Unit,
) {
    val animationDuration = 300

    ScribbleDashScaffold(
        withGradient = false,
        topAppBar = {
            ScribbleDashTopAppBar(
                title = "",
                showBackButton = false,
                showSettingsButton = false,
                optionalTitleContent = {
                    if (state.level != null && !state.showResult) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TimerCircle(
                                remainingTimeInSeconds = state.remainingTimeInSeconds,
                                isTimeLow = state.isTimeLow,
                                onTimerComplete = { onAction(SpeedDrawAction.OnDoneClick) }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            DrawCounter(
                                count = state.drawCount.toString(),
                                isNewHigh = false
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                },
                endContent = {
                    IconButton(
                        onClick = {
                            onAction(SpeedDrawAction.OnCloseClick)
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
                onLevelClick = { onAction(SpeedDrawAction.OnLevelClick(it)) },
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
                    secondToCountdown = 3,
                    strokeWidth = strokeWidth,
                    countdownEnd = {
                        if (!state.isTimerActive) {
                            // First time - start the timer
                            onAction(SpeedDrawAction.OnStartTimer)
                        } else {
                            // Subsequent examples - resume the timer
                            onAction(SpeedDrawAction.OnResumeTimer)
                        }
                    },
                    onDoneClick = { onAction(SpeedDrawAction.OnDoneClick) },
                )
            }

            AnimatedVisibility(
                visible = state.showResult,
                enter = slideInAnimation,
                exit = slideOutAnimation
            ) {
                HighScoreScreen(
                    modifier = Modifier.padding(innerPadding),
                    headerText = "Time's up!",
                    scorePercentage = "${state.finalScore.toInt()}%",
                    score = state.finalScore.toInt(),
                    drawCount = state.drawCount.toString(),
                    isNewHighScore = state.isNewHighScore,
                    onDrawAgainClick = { onAction(SpeedDrawAction.OnDrawAgainClick) },
                )
            }

        }

    }
}

@Preview
@Composable
private fun Preview() {
    ScribbleDashTheme {
        SpeedDrawScreen(
            state = SpeedDrawState(
                level = Level.Beginner,
                showResult = false,
            ),
            canvasController = DefaultCanvasController(),
            onAction = {}
        )
    }
}