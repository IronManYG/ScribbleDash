package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class OneRoundWonderViewModel(
    private val canvasController: CanvasController
) : ViewModel() {

    val canvasManager: CanvasController = canvasController

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OneRoundWonderState())
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
            initialValue = OneRoundWonderState()
        )

    fun onAction(action: OneRoundWonderAction) {
        when (action) {
            is OneRoundWonderAction.OnLevelClick -> {
                _state.value = _state.value.copy(
                    level = action.level
                )
            }

            else -> {}
        }
    }
}