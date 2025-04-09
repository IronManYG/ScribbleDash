package dev.gaddal.scribbledash.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeViewModel() : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                // Load initial data here
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

}