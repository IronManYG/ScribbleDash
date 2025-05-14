package dev.gaddal.scribbledash.gameModes.endless.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.Level

data class EndlessState(
    // Game state
    val level: Level? = null,
    val referenceResId: Int? = null,
    val showLastDrawingResult: Boolean = false,
    val showHighScoreResult: Boolean = false,
    val drawCount: Int = 0,
    val lastDrawingScore: Float = 0f,
    val totalScore: Float = 0f,
    val finalScore: Float = 0f,
    val isNewHighScore: Boolean = true,
    val isShowingExample: Boolean = true,
    val exampleCountdown: Int = 3,
)