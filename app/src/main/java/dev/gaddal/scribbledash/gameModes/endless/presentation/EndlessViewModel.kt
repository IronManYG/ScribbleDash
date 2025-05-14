package dev.gaddal.scribbledash.gameModes.endless.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.domain.gameMode.Level
import dev.gaddal.scribbledash.core.domain.statistics.StatisticsPreferences
import dev.gaddal.scribbledash.drawingCanvas.data.DrawingRepository
import dev.gaddal.scribbledash.drawingCanvas.data.ExampleDrawing
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.PathComparisonEngine
import dev.gaddal.scribbledash.drawingCanvas.domain.PathComparisonEngine.Difficulty
import dev.gaddal.scribbledash.gameModes.components.CANVAS_SIZE_PX
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class EndlessViewModel(
    private val canvasController: CanvasController,
    private val repo: DrawingRepository,
    private val engine: PathComparisonEngine,
    private val statisticsPreferences: StatisticsPreferences,
) : ViewModel() {

    val canvasManager: CanvasController = canvasController

    private var hasLoadedInitialData = false

    private val _previousHighDrawCount = MutableStateFlow(0)
    private val _previousHighestAccuracy = MutableStateFlow(0f)

    // List of drawings to cycle through/
    private var drawingsList: List<ExampleDrawing> = emptyList()
    private var currentDrawingIndex = 0
    private var isProcessingComparison = false

    private val _state = MutableStateFlow(EndlessState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                fetchStatistics()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EndlessState()
        )

    fun onAction(action: EndlessAction) {
        when (action) {
            EndlessAction.OnCloseClick -> handleCloseClick()
            is EndlessAction.OnLevelClick -> handleLevelClick(action.level)
            EndlessAction.OnDoneClick -> handleDoneClick()
            EndlessAction.OnNextDrawingClick -> handleNextDrawingClick()
            EndlessAction.OnFinishClick -> handleFinishClick()
            EndlessAction.OnDrawAgainClick -> handleDrawAgainClick()
        }
    }

    private fun fetchStatistics() {
        statisticsPreferences
            .observeMostDrawingsCompleted(mode = GameMode.EndlessMode)
            .take(1)
            .onEach { mostDrawingsCompleted ->
                _previousHighDrawCount.value = mostDrawingsCompleted
            }
            .launchIn(viewModelScope)

        statisticsPreferences
            .observeHighestAccuracy(mode = GameMode.EndlessMode)
            .take(1)
            .onEach { highestAccuracy ->
                _previousHighestAccuracy.value = highestAccuracy
            }
            .launchIn(viewModelScope)
    }

    private fun handleCloseClick() {
        _state.update { it.copy(showLastDrawingResult = false) }
        canvasController.clearCanvas()
    }

    private fun handleLevelClick(level: Level) {
        _state.update { it.copy(level = level) }
        loadDrawings()
    }

    private fun handleDoneClick() {
        if (_state.value.isShowingExample) return
        processDrawing()
    }

    private fun handleNextDrawingClick() {
        // Proceed to next drawing immediately
        advanceToNextDrawing()
        _state.update { it.copy(showLastDrawingResult = false) }
    }

    private fun handleFinishClick() {
        _state.update { it.copy(showLastDrawingResult = false, showHighScoreResult = true) }

        // Save high draw count if needed
        if (_state.value.drawCount > _previousHighDrawCount.value) {
            _previousHighDrawCount.value = _state.value.drawCount
            viewModelScope.launch {
                statisticsPreferences.saveMostDrawingsCompleted(
                    mode = GameMode.EndlessMode,
                    count = _state.value.drawCount
                )
            }
        }

        // Save highest accuracy if needed
        if (_state.value.finalScore > _previousHighestAccuracy.value) {
            _previousHighestAccuracy.value = _state.value.finalScore
            viewModelScope.launch {
                statisticsPreferences.saveHighestAccuracy(
                    mode = GameMode.EndlessMode,
                    accuracy = _state.value.finalScore
                )
            }
        }
    }

    private fun handleDrawAgainClick() {
        canvasController.clearCanvas()
        _state.value = EndlessState()
    }

    private fun showResults() {
        val isNewHighScore = _state.value.drawCount > _previousHighDrawCount.value
        Timber.tag("EndlessViewModel").d("drawCount: ${_state.value.drawCount}")
        Timber.tag("EndlessViewModel").d("previousHighDrawCount: ${_previousHighDrawCount.value}")
        Timber.tag("EndlessViewModel").d("isNewHighScore: $isNewHighScore")

        _state.update {
            it.copy(
                showLastDrawingResult = true,
                isNewHighScore = isNewHighScore
            )
        }
    }

    private fun loadDrawings() = viewModelScope.launch {
        repo.warmUp()
        drawingsList = repo.all().shuffled() // Get randomized drawings
        if (drawingsList.isNotEmpty()) {
            showNextDrawing()
        }
    }

    private fun showNextDrawing() {
        if (drawingsList.isEmpty()) return

        val example = drawingsList[currentDrawingIndex]
        _state.update {
            it.copy(
                referenceResId = example.resId,
                isShowingExample = true,
            )
        }

        // Todo : fix this by removing it here or from canvasDrawingSection
        // Start countdown for example display
        viewModelScope.launch {
            for (i in 3 downTo 1) {
                _state.update { it.copy(exampleCountdown = i) }
                delay(1000)
            }
            _state.update {
                it.copy(
                    isShowingExample = false,
                    exampleCountdown = 0
                )
            }

        }
    }

    private fun processDrawing() {
        if (isProcessingComparison) return
        isProcessingComparison = true

        val example = drawingsList[currentDrawingIndex]
        val paths = canvasManager.canvasState.value.paths

        // Process comparison in background
        viewModelScope.launch {
            val difficultyLevel = getDifficultyLevel()

            val score = engine.compare(
                userPaths = paths,
                example = example,
                difficulty = difficultyLevel,
                canvasPx = CANVAS_SIZE_PX,
                userStroke = 10f
            )

            // 1. Update lastDrawingScore in state
            _state.update { it.copy(lastDrawingScore = score) }

            // Increment counter if score is at least 70%
            if (score >= 70f) {
                updateScores(score)
            }

            isProcessingComparison = false

            // 3. Trigger showing the ResultScreen
            showResults()
        }
    }

    private fun getDifficultyLevel(): Difficulty {
        return when (state.value.level) {
            Level.Beginner -> Difficulty.BEGINNER
            Level.Master -> Difficulty.MASTER
            Level.Challenging -> Difficulty.CHALLENGING
            else -> Difficulty.BEGINNER
        }
    }

    private fun updateScores(score: Float) {
        _state.update {
            val newDrawCount = it.drawCount + 1
            val newTotalScore = it.totalScore + score
            val newFinalScore = newTotalScore / newDrawCount

            it.copy(
                drawCount = newDrawCount,
                totalScore = newTotalScore,
                finalScore = newFinalScore,
            )
        }
    }

    private fun advanceToNextDrawing() {
        canvasManager.clearCanvas()
        currentDrawingIndex = (currentDrawingIndex + 1) % drawingsList.size
        showNextDrawing()
    }

}