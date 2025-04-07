package dev.gaddal.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gaddal.scribbledash.core.presentation.designsystem.AppIcons
import dev.gaddal.scribbledash.core.presentation.designsystem.GridIconItem
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.components.ScribbleDashScaffold
import dev.gaddal.scribbledash.core.presentation.ui.LocaleUtils.LocalizedContent
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ScribbleDashTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val currentContext = LocalContext.current
    val currentLocale = rememberSaveable { mutableStateOf(Locale("en")) }

    // For Testing purposes
    LocalizedContent(
        context = currentContext,
        locale = currentLocale.value
    ) {
        ScribbleDashScaffold(
            modifier = Modifier.fillMaxSize(),
            withGradient = true,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        currentLocale.value = if (currentLocale.value.language == "en") {
                            Locale("ar")
                        } else {
                            Locale("en")
                        }
                    }
                ) {
                    Text(
                        text = if (currentLocale.value.language == "en") "AR" else "EN",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        ) { innerPadding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(8.dp)
            ) {
                Column {
                    Greeting(
                        name = stringResource(R.string.android),
                    )
                    Text(
                        text = stringResource(R.string.hello, stringResource(R.string.android)),
                        style = MaterialTheme.typography.bodyLarge
                    )
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
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.hello, name),
        modifier = modifier,
        style = MaterialTheme.typography.displayLarge
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScribbleDashTheme {
        Greeting("Android")
    }
}