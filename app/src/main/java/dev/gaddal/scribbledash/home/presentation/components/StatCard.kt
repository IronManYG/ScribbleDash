package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun StatCard(
    iconResId: Int,
    description: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        shadowElevation = 24.dp,
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 12.dp,
                        end = 20.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = iconResId),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier.size(52.dp)
                )

                Text(
                    text = description,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}