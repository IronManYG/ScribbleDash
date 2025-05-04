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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasAction
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import kotlin.math.hypot

@Composable
fun DrawingCanvas(
    canvasController: CanvasController,
    modifier: Modifier = Modifier,
    strokeWidth: Float, // injected from the selected difficulty
    touchEnabled: Boolean = true
) {
    val state by canvasController.canvasState.collectAsState()

    /* ───────── Pointer handling ───────── */
    Canvas(
        modifier = modifier
            .clipToBounds()
            .then(
                if (touchEnabled) {
                    Modifier.pointerInput(Unit) {
                        awaitPointerEventScope {
                            var lastPoint: Offset? = null
                            val coalescePx = 4f       // minimum distance before adding a new point

                            while (true) {
                                val event = awaitPointerEvent()
                                val pointer = event.changes.firstOrNull() ?: continue

                                when {
                                    pointer.pressed && !pointer.previousPressed -> {
                                        // New path
                                        lastPoint = pointer.position
                                        canvasController.handleAction(CanvasAction.NewPathStart)
                                        canvasController.handleAction(CanvasAction.Draw(pointer.position))
                                    }

                                    pointer.pressed && lastPoint != null -> {
                                        val dist = hypot(
                                            pointer.position.x - lastPoint.x,
                                            pointer.position.y - lastPoint.y
                                        )
                                        if (dist >= coalescePx) {
                                            canvasController.handleAction(CanvasAction.Draw(pointer.position))
                                            lastPoint = pointer.position
                                        }
                                    }

                                    !pointer.pressed && pointer.previousPressed -> {
                                        canvasController.handleAction(CanvasAction.PathEnd)
                                        lastPoint = null
                                    }
                                }
                                pointer.consume()
                            }
                        }
                    }
                } else {
                    Modifier // No pointer input when disabled
                }
            )
    ) {
        drawGridLines(color = AppColors.OnSurfaceVariant, density = this)

        // Draw completed paths
        state.paths.fastForEach { drawSmoothedPath(it.path, it.color, strokeWidth) }

        // Draw current in-progress path
        state.currentPath?.let {
            drawSmoothedPath(it.path, it.color, strokeWidth)
        }
    }
}

/* ───────── Drawing helpers ───────── */

fun DrawScope.drawSmoothedPath(
    points: List<Offset>,
    color: Color,
    thickness: Float
) {
    if (points.isEmpty()) return

    // Build Catmull-Rom spline → cubic Bézier once per frame.
    val path = Path().apply {
        if (points.size == 1) {
            addOval(Rect(center = points.first(), radius = thickness / 2))
        } else {
            moveTo(points.first().x, points.first().y)
            for (i in 0 until points.size - 1) {
                val p0 = if (i > 0) points[i - 1] else points[0]
                val p1 = points[i]
                val p2 = points[i + 1]
                val p3 = if (i < points.size - 2) points[i + 2] else p2

                val tension = 0.1f
                val cp1 = Offset(
                    x = p1.x + (p2.x - p0.x) * tension / 6f,
                    y = p1.y + (p2.y - p0.y) * tension / 6f
                )
                val cp2 = Offset(
                    x = p2.x - (p3.x - p1.x) * tension / 6f,
                    y = p2.y - (p3.y - p1.y) * tension / 6f
                )
                cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, p2.x, p2.y)
            }
        }
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

fun DrawScope.drawGridLines(color: Color, density: Density) {
    val strokePx = with(density) { 1.dp.toPx() }
    val stepX = size.width / 3
    val stepY = size.height / 3

    for (i in 1..2) {
        // vertical
        drawLine(
            color = color,
            start = Offset(stepX * i, 0f),
            end = Offset(stepX * i, size.height),
            strokeWidth = strokePx
        )
        // horizontal
        drawLine(
            color = color,
            start = Offset(0f, stepY * i),
            end = Offset(size.width, stepY * i),
            strokeWidth = strokePx
        )
    }
}
