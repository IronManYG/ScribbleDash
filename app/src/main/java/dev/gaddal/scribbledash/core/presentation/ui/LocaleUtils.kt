package dev.gaddal.scribbledash.core.presentation.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleUtils {
    /**
     * Updates the app's resources to use the specified locale.
     *
     * @param context The context used to get resources and system services.
     * @param locale The locale to set for the app.
     * @return Context with updated configuration
     */
    fun updateResources(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                // Use the newer API for Android 13+
                context.getSystemService(android.app.LocaleManager::class.java).applicationLocales =
                    LocaleList(locale)
                // Return the context as is since system handles the localization
                return context
            }

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                // For Android N and above, use LocaleList
                configuration.setLocales(LocaleList(locale))
            }

            else -> {
                // For older Android versions
                configuration.setLocale(locale)
            }
        }

        // Set application-wide locale via AppCompat
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))

        // Create and return a context with the new configuration
        return context.createConfigurationContext(configuration)
    }

    /**
     * Applies locale in Compose by wrapping content with appropriate context.
     *
     * @param context Base context
     * @param locale Desired locale
     * @param content Composable content to be localized
     */
    @Composable
    fun LocalizedContent(
        context: Context,
        locale: Locale,
        content: @Composable () -> Unit
    ) {
        val localizedContext = updateResources(context, locale)
        CompositionLocalProvider(
            LocalContext provides localizedContext
        ) {
            content()
        }
    }

    /**
     * Get the current locale from context
     */
    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }
}