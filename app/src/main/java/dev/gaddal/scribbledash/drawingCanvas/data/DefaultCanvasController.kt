package dev.gaddal.scribbledash.drawingCanvas.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasAction
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasState
import dev.gaddal.scribbledash.drawingCanvas.domain.PathData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultCanvasController : CanvasController {
    private val _canvasState = MutableStateFlow(CanvasState())
    override val canvasState: StateFlow<CanvasState> = _canvasState.asStateFlow()

    private val MAX_UNDO_HISTORY = 5

    override fun handleAction(action: CanvasAction) {
        when (action) {
            is CanvasAction.ClearCanvas -> clearCanvas()
            is CanvasAction.Draw -> drawAt(action.offset)
            is CanvasAction.NewPathStart -> startNewPath()
            is CanvasAction.PathEnd -> endPath()
            is CanvasAction.SelectColor -> selectColor(action.color)
            is CanvasAction.Undo -> undo()
            is CanvasAction.Redo -> redo()
        }
    }

    override fun startNewPath() {
        _canvasState.update {
            it.copy(
                currentPath = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = it.selectedColor,
                    path = emptyList()
                )
            )
        }
    }

    override fun drawAt(offset: Offset) {
        val currentPath = _canvasState.value.currentPath ?: return
        _canvasState.update {
            it.copy(
                currentPath = currentPath.copy(
                    path = currentPath.path + offset
                )
            )
        }
    }

    override fun endPath() {
        val currentPath = _canvasState.value.currentPath ?: return
        _canvasState.update {
            it.copy(
                currentPath = null,
                paths = it.paths + currentPath,
                // Clear undo stack when drawing a new path
                undoPaths = emptyList()
            )
        }
    }

    override fun selectColor(color: Color) {
        _canvasState.update { it.copy(selectedColor = color) }
    }

    override fun clearCanvas() {
        _canvasState.update {
            it.copy(
                currentPath = null,
                paths = emptyList(),
                undoPaths = emptyList() // Clear undo history too
            )
        }
    }

    override fun undo() {
        val paths = _canvasState.value.paths
        if (paths.isEmpty()) return

        val lastPath = paths.last()
        val remainingPaths = paths.dropLast(1)

        _canvasState.update {
            val updatedUndoPaths = (it.undoPaths + lastPath).takeLast(MAX_UNDO_HISTORY)
            it.copy(
                paths = remainingPaths,
                undoPaths = updatedUndoPaths
            )
        }
    }

    override fun redo() {
        val undoPaths = _canvasState.value.undoPaths
        if (undoPaths.isEmpty()) return

        val pathToRedo = undoPaths.last()
        val remainingUndoPaths = undoPaths.dropLast(1)

        _canvasState.update {
            it.copy(
                paths = it.paths + pathToRedo,
                undoPaths = remainingUndoPaths
            )
        }
    }
}