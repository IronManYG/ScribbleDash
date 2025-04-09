package dev.gaddal.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.ui.LocaleUtils.LocalizedContent
import dev.gaddal.scribbledash.navigation.RootNavGraph
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val currentContext = LocalContext.current
            val currentLocale = rememberSaveable { mutableStateOf(Locale("en")) } // Force English locale

            LocalizedContent(
                context = currentContext,
                locale = currentLocale.value
            ) {
                ScribbleDashTheme {
                    val navController = rememberNavController()
                    RootNavGraph(navController)
                }
            }
        }
    }
}