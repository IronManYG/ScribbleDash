package dev.gaddal.scribbledash.core.presentation.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.R

object AppIcons {
    val Chart: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.chart)

    val Home: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.home)

    val CloseCircle: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.close_circle)

    val ChevronLeft: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.chevron_left)

    val Forward: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.forward)

    val Reply: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.reply)
}

@Preview
@Composable
fun AppIconsPreview() {
    ScribbleDashTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            val icons = listOf(
                AppIcons.Chart to "Chart",
                AppIcons.Home to "Home",
                AppIcons.CloseCircle to "Close Circle",
                AppIcons.ChevronLeft to "Chevron Left",
                AppIcons.Forward to "Forward",
                AppIcons.Reply to "Reply",
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // You can adjust the number of columns
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(icons) { iconPair ->
                    GridIconItem(icon = iconPair.first, name = iconPair.second)
                }
            }
        }
    }
}

@Composable
fun GridIconItem(icon: ImageVector, name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = name,
            modifier = Modifier.size(32.dp) // Larger icon size for grid
        )
        Text(text = name, modifier = Modifier.padding(top = 4.dp))
    }
}