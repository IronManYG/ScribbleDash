package dev.gaddal.scribbledash.gameModes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import dev.gaddal.scribbledash.core.presentation.designsystem.CustomTextStyles.LabelExtraLarge
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashButton
import dev.gaddal.scribbledash.core.presentation.designsystem.components.util.dropShadow
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components.FeedbackSection

@Composable
fun HighScoreScreen(
    modifier: Modifier = Modifier,
    headerText: String,
    scorePercentage: String,
    score: Int,
    drawCount: String,
    isNewHighScore: Boolean = true,
    onDrawAgainClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = headerText,
            color = AppColors.OnBackgroundVariant,
            textAlign = TextAlign.Center,
            style = LabelExtraLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp)
                    .dropShadow(
                        shape = RoundedCornerShape(24.dp),
                        blur = 24.dp,
                        offsetY = 8.dp,
                    )
                    .clip(shape = RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(364.dp)
                            .heightIn(min = 112.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = scorePercentage,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Feedback Message based on Score
                    FeedbackSection(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        score = score,
                        feedbackTextColor = AppColors.OnBackgroundVariant,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    DrawCounter(
                        count = drawCount,
                        isNewHigh = isNewHighScore,
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            if (isNewHighScore) {
                NewHighScore(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .dropShadow(
                            shape = RoundedCornerShape(20.dp),
                            blur = 24.dp,
                            offsetY = 8.dp,
                        )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Draw again button
        ScribbleDashButton(
            onClick = onDrawAgainClick,
            enabled = true,
            modifier = Modifier.fillMaxWidth(0.8f),
            containerColor = MaterialTheme.colorScheme.primary,
            title = stringResource(R.string.draw_again),
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun HighScoreScreenPreview() {
    ScribbleDashTheme {
        HighScoreScreen(
            modifier = Modifier.padding(top = 32.dp),
            headerText = "Time's up!",
            scorePercentage = "100%",
            score = 100,
            drawCount = "5",
            onDrawAgainClick = {}
        )
    }
}