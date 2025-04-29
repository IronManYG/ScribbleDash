package dev.gaddal.scribbledash.drawingCanvas.domain

import android.graphics.*
import androidx.core.graphics.createBitmap
import dev.gaddal.scribbledash.drawingCanvas.data.ExampleDrawing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

class PathComparisonEngine {

    /* ─────────────────  Difficulty  ───────────────── */

    enum class Difficulty(val exampleStrokeFactor: Float) {
        BEGINNER(15f),
        CHALLENGING(7f),
        MASTER(4f)
    }

    /* ─────────────────  Public API  ───────────────── */

    /**
     * Compare the user’s drawing with the reference drawing and return an
     * accuracy score **0–100 %** (already clamped).
     */
    suspend fun compare(
        userPaths: List<PathData>,
        example: ExampleDrawing,
        difficulty: Difficulty,
        canvasPx: Int,
        userStroke: Float,
    ): Float = withContext(Dispatchers.Default) {

        /* ------------ 1  Stroke widths ------------ */
        val exampleStroke = userStroke * difficulty.exampleStrokeFactor

        /* ------------ 2  Normalise drawings ------------ */
        val normUser = normalise(
            userPaths.toAndroidPaths(),
            isUser = true,
            userStroke = userStroke,
            exampleStroke = exampleStroke,
            canvas = canvasPx
        )
        val normExample = normalise(
            example.paths,
            isUser = false,
            userStroke = userStroke,
            exampleStroke = exampleStroke,
            canvas = canvasPx
        )

        /* ------------ 3  Rasterise to bitmaps ------------ */
        val bmpUser = rasterise(normUser, userStroke, canvasPx)
        val bmpExample = rasterise(normExample, exampleStroke, canvasPx)

        /* ------------ 4  Pixel overlap ------------ */
        val (matchedUser, visibleUser) = overlap(bmpUser, bmpExample)
        val coveragePct = if (visibleUser == 0) 0f
        else matchedUser * 100f / visibleUser

        /* ------------ 5  Length penalty ------------ */
        val lenRatio = userPaths.totalLength() / example.lengthPx
        val penaltyPct = if (lenRatio < 0.7f)
            100f - (lenRatio * 100f)
        else 0f

        /* ------------ 6  Final score ------------ */
        val score = max(0f, min(100f, coveragePct - penaltyPct))

        // Clean-up bitmaps (important if many comparisons in one session)
        bmpUser.recycle()
        bmpExample.recycle()

        score
    }

    /* ────────────────────────────  Helpers  ─────────────────────────── */

    /**
     * Normalise a list of [Path]s according to the spec (inset → translate → scale).
     * @param isUser true = apply extra inset by (exampleStroke – userStroke) / 2.
     */
    private fun normalise(
        paths: List<Path>,
        isUser: Boolean,
        userStroke: Float,
        exampleStroke: Float,
        canvas: Int,
    ): List<Path> {
        /* -- a. Union all bounds -- */
        val totalBounds = RectF()
        val tmp = RectF()
        paths.forEachIndexed { index, p ->
            p.computeBounds(tmp, /*exact*/ true)
            if (index == 0) totalBounds.set(tmp) else totalBounds.union(tmp)
        }

        /* -- b. Inset by half stroke width (shrink!) -- */
        totalBounds.inset(userStroke / 2, userStroke / 2)
        if (isUser) {
            val extraInset = (exampleStroke - userStroke) / 2f
            totalBounds.inset(extraInset, extraInset)
        }

        /* -- c. Translate to origin (0,0) -- */
        val toOrigin = Matrix().apply { setTranslate(-totalBounds.left, -totalBounds.top) }

        /* -- d. Uniform scale to fill canvas -- */
        val scaleFactor = min(
            canvas / totalBounds.width(),
            canvas / totalBounds.height()
        )
        val toScale = Matrix().apply { setScale(scaleFactor, scaleFactor) }

        /* -- Apply transformations, return deep copies -- */
        return paths.map { original ->
            Path(original).apply {
                transform(toOrigin)
                transform(toScale)
            }
        }
    }

    /** Draw all [paths] onto an ALPHA_8 bitmap (opaque line, transparent bg). */
    private fun rasterise(paths: List<Path>, stroke: Float, size: Int): Bitmap =
        createBitmap(size, size, Bitmap.Config.ALPHA_8).apply {
            val canvas = Canvas(this)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = stroke
                color = Color.WHITE
            }
            paths.forEach { canvas.drawPath(it, paint) }
        }

    /** Returns Pair(matchedUserPixels, visibleUserPixels). */
    private fun overlap(user: Bitmap, example: Bitmap): Pair<Int, Int> {
        val w = user.width
        val h = user.height
        val userPixels = IntArray(w * h)
        val exPixels = IntArray(w * h)
        user.getPixels(userPixels, 0, w, 0, 0, w, h)
        example.getPixels(exPixels, 0, w, 0, 0, w, h)

        var matched = 0
        var visible = 0
        for (i in userPixels.indices) {
            if (userPixels[i] != 0) {          // user drew here
                visible++
                if (exPixels[i] != 0) {        // reference covers same pixel
                    matched++
                }
            }
        }
        return matched to visible
    }
}

/* ─────────────────  Extension utilities (private)  ───────────────── */

private fun List<PathData>.toAndroidPaths(): List<Path> =
    map { pd ->
        Path().apply {
            if (pd.path.isNotEmpty()) {
                moveTo(pd.path.first().x, pd.path.first().y)
                pd.path.drop(1).forEach { lineTo(it.x, it.y) }
            }
        }
    }

private fun List<PathData>.totalLength(): Float =
    sumOf { pd ->
        Path().apply {
            if (pd.path.isNotEmpty()) {
                moveTo(pd.path.first().x, pd.path.first().y)
                pd.path.drop(1).forEach { lineTo(it.x, it.y) }
            }
        }.let { PathMeasure(it, false).length.toDouble() }
    }.toFloat()
