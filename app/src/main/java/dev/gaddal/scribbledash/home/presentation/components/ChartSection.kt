package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R

@Composable
fun ChartSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 24.dp)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        StatCard(
            iconResId = R.drawable.stat_hourglass,
            description = stringResource(id = R.string.stat_track_empty),
            value = "0%"
        )

        Spacer(modifier = Modifier.height(12.dp))

        StatCard(
            iconResId = R.drawable.stat_bolt,
            description = stringResource(R.string.stat_track_empty),
            value = "0"
        )
    }
}