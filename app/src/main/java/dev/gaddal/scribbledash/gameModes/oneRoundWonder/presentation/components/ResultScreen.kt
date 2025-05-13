package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashButton
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.drawingCanvas.presentation.drawSmoothedPath

@Composable
fun ResultScreen(
    score: Float?,
    referenceResId: Int?,
    canvasController: CanvasController,
    innerPadding: PaddingValues,
    strokeWidth: Float,
    onTryAgainClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Score Display
        Text(
            text = "${score?.toInt()}%",
            modifier = Modifier.padding(top = 64.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Preview of the Example & The Drawing
        Row {
            DrawingPreviewCard(
                title = stringResource(id = R.string.example),
                modifier = Modifier.rotate(-10f)
            ) {
                // Draw reference image
                if (referenceResId != null) {
                    Image(
                        painter = painterResource(id = referenceResId),
                        contentDescription = stringResource(R.string.reference_drawing),
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Fit,
                    )
                }
            }

            DrawingPreviewCard(
                title = stringResource(R.string.drawing),
                modifier = Modifier
                    .padding(top = 24.dp)
                    .rotate(10f)
            ) {
                // Draw user canvas
                val state by canvasController.canvasState.collectAsStateWithLifecycle()

                Canvas(
                    modifier = Modifier
                        .clipToBounds()
                        .matchParentSize()
                ) {
                    val originalSize = CANVAS_SIZE_PX.toFloat()
                    val scaleFactor = size.minDimension / originalSize

                    scale(
                        scaleX = scaleFactor,
                        scaleY = scaleFactor,
                        pivot = Offset.Zero
                    ) {
                        state.paths.forEach { pathData ->
                            drawSmoothedPath(
                                points = pathData.path,
                                color = pathData.color,
                                thickness = strokeWidth,
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Feedback Message based on Score
        FeedbackSection(score = score?.toInt() ?: 0)

        Spacer(modifier = Modifier.weight(1f))

        // Try again button
        ScribbleDashButton(
            onClick = onTryAgainClick,
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.primary,
            title = stringResource(R.string.try_again),
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

val CANVAS_SIZE_PX: Int by lazy {
    // applicationContext gives us a density even in non-Composable code
    val density = Resources.getSystem().displayMetrics.density
    (330f * density).toInt()
}