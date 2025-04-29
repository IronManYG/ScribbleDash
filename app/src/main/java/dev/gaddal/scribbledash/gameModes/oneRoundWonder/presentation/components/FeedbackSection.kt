package dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R

@Composable
fun FeedbackSection(score: Int) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val (ratingText, feedbackResId) = when {
            score >= 90 -> stringResource(R.string.rating_woohoo) to R.string.feedback_woohoo_1 + (0..9).random()
            score >= 80 -> stringResource(R.string.rating_great) to R.string.feedback_good_1 + (0..9).random()
            score >= 70 -> stringResource(R.string.rating_good) to R.string.feedback_good_1 + (0..9).random()
            score >= 40 -> stringResource(R.string.rating_meh) to R.string.feedback_oops_1 + (0..9).random()
            else -> stringResource(R.string.rating_oops) to R.string.feedback_oops_1 + (0..9).random()
        }

        Text(
            text = ratingText,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(feedbackResId),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}