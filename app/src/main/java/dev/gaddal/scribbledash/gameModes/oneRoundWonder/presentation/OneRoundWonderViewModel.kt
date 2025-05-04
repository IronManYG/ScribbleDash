package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gaddal.scribbledash.core.domain.gameMode.Level
import dev.gaddal.scribbledash.drawingCanvas.data.DrawingRepository
import dev.gaddal.scribbledash.drawingCanvas.data.ExampleDrawing
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.PathComparisonEngine
import dev.gaddal.scribbledash.drawingCanvas.domain.PathComparisonEngine.Difficulty
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components.CANVAS_SIZE_PX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

class OneRoundWonderViewModel(
    private val canvasController: CanvasController,
    private val repo: DrawingRepository,
    private val engine: PathComparisonEngine,
) : ViewModel() {

    val canvasManager: CanvasController = canvasController

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OneRoundWonderState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // Load initial data here
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OneRoundWonderState()
        )

    fun onAction(action: OneRoundWonderAction) {
        when (action) {
            is OneRoundWonderAction.OnCloseClick -> {
                _state.value = _state.value.copy(showResult = false)
            }

            is OneRoundWonderAction.OnLevelClick -> {
                startRound(action.level)
            }

            is OneRoundWonderAction.OnDoneClick -> {
                val difficultyLevel = when (state.value.level) {
                    Level.Beginner -> Difficulty.BEGINNER
                    Level.Master -> Difficulty.CHALLENGING
                    Level.Challenging -> Difficulty.MASTER
                    else -> Difficulty.BEGINNER
                }
                evaluate(
                    difficulty = difficultyLevel,
                    canvasPx = CANVAS_SIZE_PX,
                )
                _state.value = _state.value.copy(showResult = true)
            }

            is OneRoundWonderAction.OnTryAgainClick -> {
                _state.value = _state.value.copy(
                    level = null,
                    referenceResId = null,
                    score = null,
                    showResult = false
                )
            }

            else -> {}
        }
    }

    private var currentExample: ExampleDrawing? = null

    private fun startRound(level: Level) = viewModelScope.launch {
        repo.warmUp()
        val example = repo.all().random()                 // ONE random draw
        _state.value = _state.value.copy(
            level = level,
            referenceResId = example.resId                // store for preview
        )
        currentExample = example                          // keep for compare()
    }

    /** invoked when the user taps “Done” */
    private fun evaluate(difficulty: Difficulty, canvasPx: Int, userStrokePx: Float = 10f) {
        val example = currentExample ?: return
        val paths = canvasManager.canvasState.value.paths

        if (paths.isEmpty()) {
            _state.value = _state.value.copy(score = 0f)
            Timber.tag("OneRoundWonderViewModel").d("Paths are empty")
            return
        }

        viewModelScope.launch {
            Timber.tag("OneRoundWonderViewModel")
                .d("Evaluating drawing: ${paths.size} paths, example: ${example.resId}")

            val testScore = engine.compare(
                userPaths = paths,
                example = example,
                difficulty = difficulty,
                canvasPx = canvasPx,
                userStroke = userStrokePx
            )

            Timber.tag("OneRoundWonderViewModel").d("Score is: $testScore")
            _state.value = _state.value.copy(score = testScore)
        }
    }
}
