package dev.gaddal.scribbledash.drawingCanvas.data

import android.content.Context
import android.graphics.Path
import androidx.annotation.XmlRes
import androidx.core.graphics.PathParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import timber.log.Timber

/**
 * Loads and caches reference drawings (vector XML → list<Path>).
 * Call [warmUp] once (e.g. in Application.onCreate()).
 */
class DrawingRepository(
    private val context: Context,
    @XmlRes private val resIds: List<Int>,
) {

    private var cache: List<ExampleDrawing>? = null

    /** Parse all vectors on a background thread (idempotent). */
    suspend fun warmUp() = withContext(Dispatchers.Default) {
        if (cache != null) return@withContext   // already done

        val androidNs = "http://schemas.android.com/apk/res/android"

        cache = resIds.map { resId ->
            val paths = mutableListOf<Path>()

            context.resources.getXml(resId).use { parser ->
                while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                    if (parser.eventType == XmlPullParser.START_TAG && parser.name == "path") {
                        parser.getAttributeValue(androidNs, "pathData")
                            ?.let { data ->
                                PathParser.createPathFromPathData(data)
                                    .also(paths::add)
                            }
                    }
                    parser.next()
                }
            }

            if (paths.isEmpty()) {
                Timber.tag(TAG).w("Vector #$resId contained no <path> tags!")
            }

            ExampleDrawing(
                resId = resId,
                paths = paths
            )
        }
    }

    /** Access parsed drawings – make sure [warmUp] finished first. */
    fun all(): List<ExampleDrawing> =
        cache ?: emptyList()

    companion object {
        private const val TAG = "DrawingRepository"
    }
}