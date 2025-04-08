package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme

@Composable
fun TitleAndDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleAndDescriptionPreview() {
    ScribbleDashTheme {
        TitleAndDescription(
            title = stringResource(R.string.start_drawing),
            description = stringResource(R.string.select_game_mode),
            modifier = Modifier.fillMaxWidth()
        )
    }
}