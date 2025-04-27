package dev.gaddal.scribbledash.drawingCanvas.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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

@Composable
fun DrawingCanvas(
    canvasController: CanvasController,
    modifier: Modifier = Modifier
) {
    val state by canvasController.canvasState.collectAsState()

    Canvas(
        modifier = modifier
            .clipToBounds()
            .pointerInput(Unit) {
                // Use awaitPointerEventScope for more granular control
                awaitPointerEventScope {
                    var path: MutableList<Offset>? = null
                    var lastPoint: Offset? = null

                    while (true) {
                        val event = awaitPointerEvent()
                        val pointer = event.changes.firstOrNull() ?: continue

                        when {
                            // Pointer down - start new path
                            pointer.pressed && !pointer.previousPressed -> {
                                path = mutableListOf(pointer.position)
                                lastPoint = pointer.position
                                canvasController.handleAction(CanvasAction.NewPathStart)
                                canvasController.handleAction(CanvasAction.Draw(pointer.position))
                            }

                            // Pointer moved - add to path
                            pointer.pressed && lastPoint != null -> {
                                // Always add point regardless of distance for slow movements
                                canvasController.handleAction(CanvasAction.Draw(pointer.position))
                                lastPoint = pointer.position
                            }

                            // Pointer up - end path
                            !pointer.pressed && pointer.previousPressed -> {
                                canvasController.handleAction(CanvasAction.PathEnd)
                                path = null
                                lastPoint = null
                            }
                        }

                        // Important: consume the pointer event to avoid conflicts
                        pointer.consume()
                    }
                }
            }
    ) {
        drawGridLines(color = AppColors.OnSurfaceVariant)

        // Draw completed paths
        state.paths.fastForEach { pathData ->
            drawPath(
                path = pathData.path,
                color = pathData.color,
            )
        }

        // Draw current path
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
    if (path.isEmpty()) return

    val smoothedPath = Path().apply {
        if (path.size == 1) {
            // For single points, draw a circle
            addOval(
                oval = Rect(
                    center = path.first(),
                    radius = thickness / 2
                )
            )
        } else {
            // Start path
            moveTo(path.first().x, path.first().y)

            if (path.size == 2) {
                // For only two points, draw a straight line
                lineTo(path.last().x, path.last().y)
            } else {
                // For 3+ points, use Catmull-Rom spline for smoother curves
                for (i in 0 until path.size - 1) {
                    val p0 = if (i > 0) path[i - 1] else path[0]
                    val p1 = path[i]
                    val p2 = path[i + 1]
                    val p3 = if (i < path.size - 2) path[i + 2] else p2

                    // Catmull-Rom to Cubic Bezier conversion
                    val tension = 0.1f // Controls curve tightness (0.0-1.0)

                    val cp1x = p1.x + (p2.x - p0.x) * tension / 6
                    val cp1y = p1.y + (p2.y - p0.y) * tension / 6
                    val cp2x = p2.x - (p3.x - p1.x) * tension / 6
                    val cp2y = p2.y - (p3.y - p1.y) * tension / 6

                    cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
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