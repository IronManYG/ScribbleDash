package dev.gaddal.scribbledash.drawingCanvas.domain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.StateFlow

interface CanvasController {
    val canvasState: StateFlow<CanvasState>

    fun startNewPath()
    fun drawAt(offset: Offset)
    fun endPath()
    fun selectColor(color: Color)
    fun clearCanvas()
    fun undo()
    fun redo()

    // Helper method for handling actions
    fun handleAction(action: CanvasAction)
}