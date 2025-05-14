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
import dev.gaddal.scribbledash.home.presentation.HomeState

@Composable
fun ChartSection(
    state: HomeState,
    modifier: Modifier = Modifier,
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
            description = if (state.highestAccuracySpeedDraw != 0f) "Highest Speed Draw accuracy%"
            else stringResource(R.string.stat_track_empty),
            value = "${state.highestAccuracySpeedDraw.toInt()}%"
        )

        Spacer(modifier = Modifier.height(12.dp))

        StatCard(
            iconResId = R.drawable.stat_bolt,
            description = if (state.mostDrawingsCompletedSpeedDraw != 0) "Most Meh+ drawings in Speed Draw"
            else stringResource(R.string.stat_track_empty),
            value = "${state.mostDrawingsCompletedSpeedDraw}"
        )

        Spacer(modifier = Modifier.height(12.dp))

        StatCard(
            iconResId = R.drawable.star,
            description = if (state.highestAccuracyEndlessMode != 0f) "Highest Endless Mode accuracy%"
            else stringResource(R.string.stat_track_empty),
            value = "${state.highestAccuracyEndlessMode.toInt()}%"
        )

        Spacer(modifier = Modifier.height(12.dp))

        StatCard(
            iconResId = R.drawable.palette,
            description = if (state.mostDrawingsCompletedEndlessMode != 0) "Most drawings in Endless Mode"
            else stringResource(R.string.stat_track_empty),
            value = "${state.mostDrawingsCompletedEndlessMode}"
        )
    }
}