package dev.gaddal.scribbledash.drawingCanvas.data

import android.graphics.Path
import android.graphics.PathMeasure
import androidx.annotation.XmlRes

/**
 * Represents a reference drawing loaded from an XML vector-drawable.
 *
 * This class stores the parsed path data from SVG files that serve as reference drawings in the game.
 * Players attempt to recreate these drawings, and their attempts are scored by comparing their paths against these reference examples.
 * Instances of this class are created by [DrawingRepository] during initialization
 * and used by the comparison engine to calculate similarity scores.
 *
 * @property resId Resource ID of the XML vector-drawable
 * @property paths Collection of paths extracted from the SVG as Android Path objects
 * @property lengthPx Total outline length of all paths combined, used for scoring calculations */
data class ExampleDrawing(
    @XmlRes val resId: Int,
    val paths: List<Path>,
) {
    val lengthPx: Float by lazy {
        paths.sumOf { PathMeasure(it, /*forceClosed=*/ false).length.toDouble() }
            .toFloat()
    }
}
