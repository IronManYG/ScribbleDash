package dev.gaddal.scribbledash.gameModes.speedDraw.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.Level

data class SpeedDrawState(
    // Game state
    val level: Level? = null,
    val referenceResId: Int? = null,
    val showResult: Boolean = false,
    val drawCount: Int = 0,
    val totalScore: Float = 0f,
    val finalScore: Float = 0f,
    val isNewHighScore: Boolean = true,
    val isShowingExample: Boolean = true,
    val exampleCountdown: Int = 3,

    // Timer state
    val isTimerActive: Boolean = false,
    val isTimerPaused: Boolean = false,
    val remainingTimeInSeconds: Int = 120,
    val isTimeLow: Boolean = false,
)