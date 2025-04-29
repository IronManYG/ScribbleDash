package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.Level

sealed interface OneRoundWonderAction {
    data object OnCloseClick : OneRoundWonderAction
    data class OnLevelClick(val level: Level) : OneRoundWonderAction
    data object OnDoneClick : OneRoundWonderAction
    data object OnTryAgainClick : OneRoundWonderAction
}