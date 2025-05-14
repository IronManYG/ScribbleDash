package dev.gaddal.scribbledash.gameModes.speedDraw.presentation

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
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components.CANVAS_SIZE_PX
import dev.gaddal.scribbledash.gameModes.speedDraw.domain.SpeedDrawTimerTracker
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

class SpeedDrawViewModel(
    private val canvasController: CanvasController,
    private val repo: DrawingRepository,
    private val engine: PathComparisonEngine,
    private val statisticsPreferences: StatisticsPreferences,
) : ViewModel() {

    val canvasManager: CanvasController = canvasController
    private val timerTracker = SpeedDrawTimerTracker(
        applicationScope = viewModelScope,
        onTimerComplete = ::handleTimerComplete,
    )

    private var hasLoadedInitialData = false

    private val _previousHighDrawCount = MutableStateFlow(0)
    private val _previousHighestAccuracy = MutableStateFlow(0f)

    // List of drawings to cycle through/
    private var drawingsList: List<ExampleDrawing> = emptyList()
    private var currentDrawingIndex = 0
    private var isProcessingComparison = false

    private val _state = MutableStateFlow(SpeedDrawState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                observeTimerTracker()
                fetchStatistics()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SpeedDrawState()
        )

    fun onAction(action: SpeedDrawAction) {
        when (action) {
            SpeedDrawAction.OnCloseClick -> handleCloseClick()
            is SpeedDrawAction.OnLevelClick -> handleLevelClick(action.level)
            SpeedDrawAction.OnDoneClick -> handleDoneClick()
            SpeedDrawAction.OnDrawAgainClick -> handleDrawAgainClick()
            SpeedDrawAction.OnStartTimer -> handleStartTimer()
            SpeedDrawAction.OnResumeTimer -> handleResumeTimer()
        }
    }

    private fun fetchStatistics() {
        statisticsPreferences
            .observeMostDrawingsCompleted(mode = GameMode.SpeedDraw)
            .take(1)
            .onEach { mostDrawingsCompleted ->
                _previousHighDrawCount.value = mostDrawingsCompleted
            }
            .launchIn(viewModelScope)

        statisticsPreferences
            .observeHighestAccuracy(mode = GameMode.SpeedDraw)
            .take(1)
            .onEach { highestAccuracy ->
                _previousHighestAccuracy.value = highestAccuracy
            }
            .launchIn(viewModelScope)
    }

    private fun observeTimerTracker() {
        timerTracker.isActive
            .onEach { isActive ->
                _state.update { it.copy(isTimerActive = isActive) }
            }
            .launchIn(viewModelScope)

        timerTracker.isPaused
            .onEach { paused ->
                _state.update { it.copy(isTimerPaused = paused) }
            }
            .launchIn(viewModelScope)

        timerTracker.remainingTimeInSeconds
            .onEach { remainingTime ->
                _state.update { it.copy(remainingTimeInSeconds = remainingTime) }
            }
            .launchIn(viewModelScope)


        timerTracker.isTimeLow
            .onEach { isTimeLow ->
                _state.update { it.copy(isTimeLow = isTimeLow) }
            }
            .launchIn(viewModelScope)
    }

    private fun handleCloseClick() {
        _state.update { it.copy(showResult = false) }
        timerTracker.stopTimer()
        canvasController.clearCanvas()
    }

    private fun handleLevelClick(level: Level) {
        _state.update { it.copy(level = level) }
        loadDrawings()
    }

    private fun handleDoneClick() {
        if (_state.value.isShowingExample) return

        if (_state.value.remainingTimeInSeconds <= 0) {
            showResults()
        } else {
            processDrawing()
        }
    }

    private fun handleDrawAgainClick() {
        canvasController.clearCanvas()
        _state.value = SpeedDrawState()
        timerTracker.stopTimer()
        timerTracker.resetTimer()
    }

    private fun handleStartTimer() {
        timerTracker.startTimer(resetTime = true)
    }

    private fun handleResumeTimer() {
        timerTracker.resumeTimer()
    }

    private fun handleTimerComplete() {
        showResults()
    }

    private fun showResults() {
        _state.update {
            it.copy(
                showResult = true,
                isNewHighScore = it.drawCount > _previousHighDrawCount.value
            )
        }

        // Save high draw count if needed
        if (_state.value.drawCount > _previousHighDrawCount.value) {
            _previousHighDrawCount.value = _state.value.drawCount
            viewModelScope.launch {
                statisticsPreferences.saveMostDrawingsCompleted(
                    mode = GameMode.SpeedDraw,
                    count = _state.value.drawCount
                )
            }
        }

        // Save highest accuracy if needed
        if (_state.value.finalScore > _previousHighestAccuracy.value) {
            _previousHighestAccuracy.value = _state.value.finalScore
            viewModelScope.launch {
                statisticsPreferences.saveHighestAccuracy(
                    mode = GameMode.SpeedDraw,
                    accuracy = _state.value.finalScore
                )
            }
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

        // Pause the timer during example display
        timerTracker.pauseTimer()

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
            // Resume timer when example display ends
            if (_state.value.isTimerActive) {
                timerTracker.resumeTimer()
            }
        }
    }

    private fun processDrawing() {
        if (isProcessingComparison) return
        isProcessingComparison = true

        val example = drawingsList[currentDrawingIndex]
        val paths = canvasManager.canvasState.value.paths

        // Proceed to next drawing immediately
        advanceToNextDrawing()

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

            // Increment counter if score is at least 40%
            if (score >= 40f) {
                updateScores(score)
            }

            isProcessingComparison = false
        }
    }

    private fun getDifficultyLevel(): Difficulty {
        return when (state.value.level) {
            Level.Beginner -> Difficulty.BEGINNER
            Level.Master -> Difficulty.CHALLENGING
            Level.Challenging -> Difficulty.MASTER
            else -> Difficulty.BEGINNER
        }
    }

    private fun updateScores(score: Float) {
        _state.update {
            val newDrawCount = it.drawCount + 1
            val newTotalScore = it.totalScore + score
            val newFinalScore = if (newDrawCount == 0) 0f else newTotalScore / newDrawCount

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