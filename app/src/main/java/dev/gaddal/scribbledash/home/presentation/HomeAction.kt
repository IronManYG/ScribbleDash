package dev.gaddal.scribbledash.home.presentation

import dev.gaddal.scribbledash.core.domain.gameMode.GameMode

sealed interface HomeAction {
    data object OnHomeGameModeClick : HomeAction
    data object OnChartClick : HomeAction
    data class OnGameModeClick(val gameMode: GameMode) : HomeAction
}