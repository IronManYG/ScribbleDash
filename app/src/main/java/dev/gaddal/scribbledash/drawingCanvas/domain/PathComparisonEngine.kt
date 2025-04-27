package dev.gaddal.scribbledash.drawingCanvas.domain

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import androidx.core.graphics.createBitmap
import dev.gaddal.scribbledash.drawingCanvas.data.ExampleDrawing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

class PathComparisonEngine {

    enum class Difficulty(val exampleStrokeFactor: Float) {
        BEGINNER(15f), CHALLENGING(7f), MASTER(4f)
    }

    /**
     * Compare user drawing against example drawing.
     *
     * @param userPaths   the paths the user drew (your `CanvasState.paths`)
     * @param canvasPx    square canvas size *in px* that you rendered on screen
     * @param userStroke  stroke-width the user actually used while drawing
     * @return percentage 0 – 100
     */
    suspend fun compare(
        userPaths: List<PathData>,
        example: ExampleDrawing,
        difficulty: Difficulty,
        canvasPx: Int,
        userStroke: Float,
    ): Float = withContext(Dispatchers.Default) {

        /* ---------- 1. Prepare stroke widths ---------- */
        val exStroke = userStroke * difficulty.exampleStrokeFactor

        /* ---------- 2. Normalise both drawings ---------- */
        val normUser = normalise(userPaths.toAndroidPaths(), userStroke, canvasPx)
        val normEx = normalise(example.paths.map { Path(it) }, exStroke, canvasPx)

        /* ---------- 3. Rasterise to bitmaps ---------- */
        val bmpUser = rasterise(normUser, userStroke, canvasPx)
        val bmpEx = rasterise(normEx, exStroke, canvasPx)

        /* ---------- 4. Pixel-wise overlap ---------- */
        val (match, visible) = overlap(bmpUser, bmpEx)
        val coverage = if (visible == 0) 0f else match * 100f / visible

        /* ---------- 5. Path-length penalty ---------- */
        val lenRatio = userPaths.totalLength() / example.lengthPx
        val penalty = when {
            lenRatio < 0.5f -> 50f - (lenRatio * 100f) // Less harsh penalty
            lenRatio < 0.7f -> 25f - (lenRatio * 25f)  // Minimal penalty between 0.5-0.7
            else -> 0f
        }


        /* ---------- 6. Final score ---------- */
        max(0f, min(100f, coverage - penalty))
    }

    /* ===== helpers ===== */

    private fun normalise(
        paths: List<Path>,
        stroke: Float,
        canvas: Int,
    ): List<Path> {
        val box = RectF()
        paths.forEach { it.computeBounds(box, true) }
        box.inset(-stroke / 2, -stroke / 2)                // (b)

        val toOrigin = Matrix().apply { setTranslate(-box.left, -box.top) }   // (c)
        val scale = min(canvas / box.width(), canvas / box.height())           // (d)
        val toScale = Matrix().apply { setScale(scale, scale) }

        return paths.map { Path(it).apply { transform(toOrigin); transform(toScale) } }
    }

    private fun rasterise(
        paths: List<Path>,
        stroke: Float,
        size: Int,
        dilate: Boolean = false
    ): Bitmap =
        createBitmap(size, size, Bitmap.Config.ALPHA_8).apply {
            val c = Canvas(this)
            val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = if (dilate) stroke * 1.5f else stroke
                color = Color.WHITE
            }
            paths.forEach { c.drawPath(it, p) }
        }

    private fun overlap(u: Bitmap, e: Bitmap): Pair<Int, Int> {
        val w = u.width
        val h = u.height
        val up = IntArray(w * h)
        val ep = IntArray(w * h)
        u.getPixels(up, 0, w, 0, 0, w, h)
        e.getPixels(ep, 0, w, 0, 0, w, h)

        var match = 0
        var visible = 0
        for (i in up.indices) {
            if (up[i] != 0) {              // user pixel
                visible++
                // Check adjacent pixels in a small radius too
                if (ep[i] != 0 || (i > w && ep[i-w] != 0) || (i < ep.size-w && ep[i+w] != 0) ||
                    (i % w > 0 && ep[i-1] != 0) || (i % w < w-1 && ep[i+1] != 0)) {
                    match++
                }
            }
        }
        return match to visible
    }
}

/* ---------- path helpers (private ext.) ---------- */

private fun List<PathData>.toAndroidPaths(): List<Path> =
    map { pd ->
        Path().apply {
            if (pd.path.isNotEmpty()) {
                moveTo(pd.path.first().x, pd.path.first().y)
                pd.path.drop(1).forEach { p -> lineTo(p.x, p.y) }
            }
        }
    }

private fun List<PathData>.totalLength(): Float =
    map { pd ->
        Path().apply {
            if (pd.path.isNotEmpty()) {
                moveTo(pd.path.first().x, pd.path.first().y)
                pd.path.drop(1).forEach { p -> lineTo(p.x, p.y) }
            }
        }
    }.sumOf { PathMeasure(it, false).length.toDouble() }   // Double
        .toFloat()