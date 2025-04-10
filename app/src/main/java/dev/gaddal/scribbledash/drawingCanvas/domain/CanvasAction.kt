package dev.gaddal.scribbledash.drawingCanvas.domain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

sealed interface CanvasAction {
    data object NewPathStart : CanvasAction
    data class Draw(val offset: Offset) : CanvasAction
    data object PathEnd : CanvasAction
    data class SelectColor(val color: Color) : CanvasAction
    data object ClearCanvas : CanvasAction
    data object Undo : CanvasAction
    data object Redo : CanvasAction
}