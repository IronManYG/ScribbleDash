package dev.gaddal.scribbledash.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.domain.statistics.StatisticsPreferences
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val statisticsPreferences: StatisticsPreferences,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // Load initial data here
                observeStatistics()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnHomeGameModeClick -> {
                _state.value = _state.value.copy(
                    currentDestination = HomeNavItem.HomeGameMode
                )
            }

            is HomeAction.OnChartClick -> {
                _state.value = _state.value.copy(
                    currentDestination = HomeNavItem.Chart
                )
            }

            else -> {}
        }
    }

    private fun observeStatistics() {
        combine(
            statisticsPreferences.observeHighestAccuracy(GameMode.SpeedDraw),
            statisticsPreferences.observeMostDrawingsCompleted(GameMode.SpeedDraw),
            statisticsPreferences.observeHighestAccuracy(GameMode.EndlessMode),
            statisticsPreferences.observeMostDrawingsCompleted(GameMode.EndlessMode),
        ) { highestAccuracySpeedDraw, mostDrawingsCompletedSpeedDraw, highestAccuracyEndlessMode, mostDrawingsCompletedEndlessMode ->
            _state.update {
                it.copy(
                    highestAccuracySpeedDraw = highestAccuracySpeedDraw,
                    mostDrawingsCompletedSpeedDraw = mostDrawingsCompletedSpeedDraw,
                    highestAccuracyEndlessMode = highestAccuracyEndlessMode,
                    mostDrawingsCompletedEndlessMode = mostDrawingsCompletedEndlessMode
                )
            }
        }.launchIn(viewModelScope)
    }

}