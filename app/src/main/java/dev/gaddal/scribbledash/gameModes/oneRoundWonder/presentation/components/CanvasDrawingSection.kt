package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.drawingCanvas.data.DefaultCanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.drawingCanvas.presentation.CanvasControls
import dev.gaddal.scribbledash.drawingCanvas.presentation.DrawingCanvas

@Composable
fun CanvasDrawingSection(
    canvasController: CanvasController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // title
        Text(
            text = stringResource(R.string.time_to_draw),
            modifier = Modifier.padding(top = 32.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            modifier = Modifier
                .aspectRatio(1f) // Ensures 1:1 aspect ratio
                .size(354.dp),
            shape = RoundedCornerShape(36.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 24.dp,
        ) {
            DrawingCanvas(
                canvasController = canvasController,
                modifier = Modifier
                    .padding(12.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clip(shape = RoundedCornerShape(24.dp))
                    .aspectRatio(1f) // Ensures 1:1 aspect ratio
                    .background(MaterialTheme.colorScheme.surface)
                    .size(330.dp),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CanvasControls(
            canvasController = canvasController,
            showColorControls = false,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun CanvasDrawingSectionPreview() {
    ScribbleDashTheme {
        CanvasDrawingSection(
            canvasController = DefaultCanvasController(),
        )
    }
}