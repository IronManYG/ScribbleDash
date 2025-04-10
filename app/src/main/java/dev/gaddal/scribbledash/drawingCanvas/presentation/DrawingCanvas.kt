package dev.gaddal.scribbledash.drawingCanvas.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastForEach
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasAction
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import kotlin.math.abs

@Composable
fun DrawingCanvas(
    canvasController: CanvasController,
    modifier: Modifier = Modifier
) {
    val state by canvasController.canvasState.collectAsState()

    Canvas(
        modifier = modifier
            .clipToBounds()
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        canvasController.handleAction(CanvasAction.NewPathStart)
                    },
                    onDragEnd = {
                        canvasController.handleAction(CanvasAction.PathEnd)
                    },
                    onDrag = { change, _ ->
                        canvasController.handleAction(CanvasAction.Draw(change.position))
                    },
                    onDragCancel = {
                        canvasController.handleAction(CanvasAction.PathEnd)
                    },
                )
            }
    ) {
        drawGridLines(color = AppColors.OnSurfaceVariant)
        state.paths.fastForEach { pathData ->
            drawPath(
                path = pathData.path,
                color = pathData.color,
            )
        }
        state.currentPath?.let {
            drawPath(
                path = it.path,
                color = it.color
            )
        }
    }
}

private fun DrawScope.drawPath(
    path: List<Offset>,
    color: Color,
    thickness: Float = 10f
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {
            moveTo(path.first().x, path.first().y)

            val smoothness = 5
            for (i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2f,
                        y1 = (from.y + to.y) / 2f,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

private fun DrawScope.drawGridLines(color: Color) {
    val stepX = size.width / 3
    val stepY = size.height / 3

    for (i in 1..2) {
        // Draw vertical lines
        drawLine(
            color = color,
            start = Offset(x = stepX * i, y = 0f),
            end = Offset(x = stepX * i, y = size.height),
            strokeWidth = 2f
        )
        // Draw horizontal lines
        drawLine(
            color = color,
            start = Offset(x = 0f, y = stepY * i),
            end = Offset(x = size.width, y = stepY * i),
            strokeWidth = 2f
        )
    }
}