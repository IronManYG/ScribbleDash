package dev.gaddal.scribbledash.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.gaddal.scribbledash.core.presentation.ui.LocaleUtils

/**
 * Gets the appropriate font family based on the current locale
 */
@Composable
@ReadOnlyComposable
fun getLocalizedDisplayFontFamily(): FontFamily {
    val context = LocalContext.current
    val currentLocale = LocaleUtils.getCurrentLocale(context)

    return when (currentLocale.language) {
        "ar" -> OiFontFamily // Arabic font for display text
        else -> BagelFatOneFontFamily // Default font for other languages
    }
}

@Composable
@ReadOnlyComposable
fun getLocalizedBodyFontFamily(): FontFamily {
    val context = LocalContext.current
    val currentLocale = LocaleUtils.getCurrentLocale(context)

    return when (currentLocale.language) {
        "ar" -> OutfitFontFamily // Arabic font for body text
        else -> OutfitFontFamily // Default font for other languages
    }
}

/**
 * Localized Typography provides typography styles that adapt based on the current locale
 */
val Typography: Typography
    @Composable
    @ReadOnlyComposable
    get() {
        val displayFontFamily = getLocalizedDisplayFontFamily()
        val bodyFontFamily = getLocalizedBodyFontFamily()

        return Typography(
            // ====================== DISPLAY FONTS (Localized) =====================

            // Display Large
            displayLarge = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 66.sp,
                lineHeight = 80.sp
            ),
            // Display Medium
            displayMedium = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 40.sp,
                lineHeight = 44.sp
            ),

            // Headline Large
            headlineLarge = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 34.sp,
                lineHeight = 48.sp
            ),
            // Headline Medium
            headlineMedium = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 26.sp,
                lineHeight = 30.sp
            ),
            // Headline Small
            headlineSmall = TextStyle(
                fontFamily = displayFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                lineHeight = 26.sp
            ),

            // ====================== BODY FONTS (Localized) =====================

            // Body Large
            bodyLarge = TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                lineHeight = 24.sp
            ),
            // Body Medium
            bodyMedium = TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            // Body Small
            bodySmall = TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 18.sp
            ),

            // Label Large
            labelLarge = TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                lineHeight = 24.sp
            ),
            // Label Medium
            labelMedium = TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 24.sp
            ),
            // Label Small
            labelSmall = TextStyle(
                fontFamily = bodyFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 18.sp
            ),
        )
    }

/**
 * CustomTextStyles defines additional custom text styles for the ScribbleDash app.
 * These styles are not part of the standard Material 3 typography.
 */
object CustomTextStyles {
    /**
     * Gets a localized custom XSmall style
     */
    val XSmall: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontFamily = getLocalizedDisplayFontFamily(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )

    /**
     * Gets a localized custom Label Extra Large style
     */
    val LabelExtraLarge: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontFamily = getLocalizedBodyFontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        )
}