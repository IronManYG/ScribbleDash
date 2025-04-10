package dev.gaddal.scribbledash.drawingCanvas.domain

import androidx.compose.ui.graphics.Color

data class CanvasState(
    val selectedColor: Color = Color.Black,
    val currentPath: PathData? = null,
    val paths: List<PathData> = emptyList(),
    val undoPaths: List<PathData> = emptyList() // Stack for redo operations
)