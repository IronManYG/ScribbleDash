package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.Level

data class OneRoundWonderState(
    val level: Level? = null,
    val referenceResId: Int? = null,
    val score: Float? = null,
    val showResult: Boolean = false
)

