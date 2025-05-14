package dev.gaddal.scribbledash.gameModes.endless.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.Level

sealed interface EndlessAction {
    data object OnCloseClick : EndlessAction
    data class OnLevelClick(val level: Level) : EndlessAction
    data object OnDoneClick : EndlessAction
    data object OnNextDrawingClick : EndlessAction
    data object OnFinishClick : EndlessAction
    data object OnDrawAgainClick : EndlessAction
}