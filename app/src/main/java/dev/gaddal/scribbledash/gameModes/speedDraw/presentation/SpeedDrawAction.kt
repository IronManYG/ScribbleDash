package dev.gaddal.scribbledash.gameModes.speedDraw.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.Level

sealed interface SpeedDrawAction {
    data object OnCloseClick : SpeedDrawAction
    data class OnLevelClick(val level: Level) : SpeedDrawAction
    data object OnDoneClick : SpeedDrawAction
    data object OnDrawAgainClick : SpeedDrawAction

    // Timer actions
    data object OnStartTimer : SpeedDrawAction
    data object OnResumeTimer : SpeedDrawAction
}