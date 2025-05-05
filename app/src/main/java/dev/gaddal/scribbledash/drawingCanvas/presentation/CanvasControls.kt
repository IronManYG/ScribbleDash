package dev.gaddal.scribbledash.drawingCanvas.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.AppIcons
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashButton
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasAction
import dev.gaddal.scribbledash.drawingCanvas.domain.CanvasController

@Composable
fun ColumnScope.CanvasControls(
    canvasController: CanvasController,
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        Color.Black,
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Gray,
        Color.White
    ),
    showColorControls: Boolean = true,
    actionButton: @Composable (() -> Unit)? = null
) {
    val state by canvasController.canvasState.collectAsState()

    if (showColorControls) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            colors.fastForEach { color ->
                val isSelected = state.selectedColor == color
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val scale = if (isSelected) 1.2f else 1f
                            scaleX = scale
                            scaleY = scale
                        }
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            canvasController.handleAction(CanvasAction.SelectColor(color))
                        }
                )
            }
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.size(64.dp),
            onClick = {
                canvasController.handleAction(CanvasAction.Undo)
            },
            enabled = state.paths.isNotEmpty(),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.SurfaceLow,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                imageVector = AppIcons.Reply,
                contentDescription = "Undo",
                modifier = Modifier.size(28.dp)
            )
        }

        Button(
            modifier = Modifier.size(64.dp),
            onClick = {
                canvasController.handleAction(CanvasAction.Redo)
            },
            enabled = state.undoPaths.isNotEmpty(),
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.SurfaceLow,
                contentColor = MaterialTheme.colorScheme.onBackground,
            ),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                imageVector = AppIcons.Forward,
                contentDescription = "Redo",
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (actionButton != null) {
            actionButton()
        } else {
            ScribbleDashButton(
                onClick = { canvasController.handleAction(CanvasAction.ClearCanvas) },
                enabled = state.paths.isNotEmpty(),
                title = stringResource(R.string.clear_canvas),
            )
        }
    }
}