package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashButton
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow
import dev.gaddal.scribbledash.drawingCanvas.data.DefaultCanvasController
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController
import dev.gaddal.scribbledash.drawingCanvas.presentation.CanvasControls
import dev.gaddal.scribbledash.drawingCanvas.presentation.DrawingCanvas
import kotlinx.coroutines.delay

@Composable
fun CanvasDrawingSection(
    canvasController: CanvasController,
    modifier: Modifier = Modifier,
    strokeWidth: Float,
    referenceDrawingResId: Int?,
    onDoneClick: () -> Unit
) {
    val state by canvasController.canvasState.collectAsState()

    // Countdown state
    var countdownSeconds by remember { mutableIntStateOf(5) }
    var isCountdownActive by remember { mutableStateOf(true) }

    // Timer effect
    LaunchedEffect(key1 = Unit) {
        while (countdownSeconds > 0) {
            delay(1000L)
            countdownSeconds--
        }
        isCountdownActive = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title changes based on countdown state
        Text(
            text = if (isCountdownActive) stringResource(R.string.ready_set) else stringResource(R.string.time_to_draw),
            modifier = Modifier.padding(top = 32.dp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Surface(
            modifier = Modifier
                .dropShadow(
                    shape = RoundedCornerShape(36.dp),
                    blur = 24.dp,
                    offsetY = 8.dp,
                )
                .aspectRatio(1f)
                .size(354.dp),
            shape = RoundedCornerShape(36.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clip(shape = RoundedCornerShape(24.dp))
                    .aspectRatio(1f)
                    .background(MaterialTheme.colorScheme.surface)
                    .size(330.dp),
                contentAlignment = Alignment.Center
            ) {
                // Drawing canvas with disabled touch during countdown
                DrawingCanvas(
                    canvasController = canvasController,
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = strokeWidth,
                    touchEnabled = !isCountdownActive
                )

                // Reference drawing during countdown
                if (referenceDrawingResId != null && isCountdownActive) {
                    Image(
                        painter = painterResource(id = referenceDrawingResId),
                        contentDescription = "Reference drawing",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        alpha = if (isCountdownActive) DefaultAlpha else 0.25f
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isCountdownActive) stringResource(R.string.example) else stringResource(R.string.your_drawing),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.weight(1f))

        // Show countdown text or controls based on state
        if (isCountdownActive) {
            Text(
                text = stringResource(R.string.seconds_left, countdownSeconds),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            CanvasControls(
                canvasController = canvasController,
                showColorControls = false,
                actionButton = {
                    // Done Button
                    ScribbleDashButton(
                        onClick = onDoneClick,
                        enabled = state.paths.isNotEmpty(),
                        title = stringResource(R.string.done),
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Preview(showBackground = true)
@Composable
private fun CanvasDrawingSectionPreview() {
    ScribbleDashTheme {
        CanvasDrawingSection(
            canvasController = DefaultCanvasController(),
            strokeWidth = 15f,
            referenceDrawingResId = R.drawable.whale_drawing,
            onDoneClick = {},
        )
    }
}