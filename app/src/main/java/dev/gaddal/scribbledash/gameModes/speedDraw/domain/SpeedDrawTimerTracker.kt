package dev.gaddal.scribbledash.gameModes.speedDraw.domain

import dev.gaddal.scribbledash.core.domain.util.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class SpeedDrawTimerTracker(
    private val applicationScope: CoroutineScope,
    private val onTimerComplete: () -> Unit = {},
    private val initialTimeInSeconds: Int = 120
) {
    // Raw states
    private val _isActive = MutableStateFlow(false)
    val isActive = _isActive.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused = _isPaused.asStateFlow()

    private val _remainingTimeInSeconds = MutableStateFlow(initialTimeInSeconds)
    val remainingTimeInSeconds = _remainingTimeInSeconds.asStateFlow()

    // Computed state
    val isTimeLow: StateFlow<Boolean> = _remainingTimeInSeconds
        .map { it <= 30 }
        .distinctUntilChanged()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = false
        )

    init {
        // Only run timer when active and not paused
        val isActiveAndNotPaused = combine(_isActive, _isPaused) { active, paused ->
            active && !paused
        }.distinctUntilChanged()

        isActiveAndNotPaused
            .flatMapLatest { running ->
                if (running) Timer.createCountdownFlow(
                    totalTimeSeconds = _remainingTimeInSeconds.value
                ) else flowOf()
            }
            .onEach { remainingSeconds ->
                _remainingTimeInSeconds.value = remainingSeconds

                // Auto-stop when time reaches zero
                if (remainingSeconds <= 0) {
                    stopTimer()
                    resetTimer()
                    onTimerComplete()
                }
            }
            .launchIn(applicationScope)
    }

    fun startTimer(resetTime: Boolean = true) {
        if (resetTime) {
            _remainingTimeInSeconds.value = initialTimeInSeconds
        }
        _isActive.value = true
        _isPaused.value = false
    }

    fun pauseTimer() {
        if (!_isActive.value || _isPaused.value) return
        _isPaused.value = true
    }

    fun resumeTimer() {
        if (!_isActive.value || !_isPaused.value) return
        _isPaused.value = false
    }

    fun stopTimer() {
        _isActive.value = false
        _isPaused.value = false
    }

    fun resetTimer() {
        _remainingTimeInSeconds.value = initialTimeInSeconds
    }
}