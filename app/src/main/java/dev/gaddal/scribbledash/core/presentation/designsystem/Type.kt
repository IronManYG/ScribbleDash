package dev.gaddal.scribbledash.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typography defines the typography styles for the ScribbleDash app.
 */
val Typography = Typography(

    // ====================== BAGEL FAT ONE (Displays & Headlines) =====================

    // Display Large (Bagel Fat One)
    displayLarge = TextStyle(
        fontFamily = BagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 66.sp,
        lineHeight = 80.sp
    ),
    // Display Medium (Bagel Fat One)
    displayMedium = TextStyle(
        fontFamily = BagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 44.sp
    ),

    // Headline Large (Bagel Fat One)
    headlineLarge = TextStyle(
        fontFamily = BagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 48.sp
    ),
    // Headline Medium (Bagel Fat One)
    headlineMedium = TextStyle(
        fontFamily = BagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp
    ),
    // Headline Small (Bagel Fat One)
    headlineSmall = TextStyle(
        fontFamily = BagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 26.sp
    ),

    // ====================== OUTFIT (Bodies & Labels) =====================

    // Body Large (Outfit)
    bodyLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    // Body Medium (Outfit)
    bodyMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    // Body Small (Outfit)
    bodySmall = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),

    // Label Large (Outfit)
    labelLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    // Label Medium (Outfit)
    labelMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    // Label Small (Outfit)
    labelSmall = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
)

/**
 * CustomTextStyles defines additional custom text styles for the ScribbleDash app.
 * These styles are not part of the standard Material 3 typography.
 */
object CustomTextStyles {
    /**
     * BagelFatOneXSmall is a custom style for “x-small” text,
     * which is outside the standard M3 set.
     */
    val BagelFatOneXSmall = TextStyle(
        fontFamily = BagelFatOneFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    /**
     * OutfitLabelExtraLarge is a custom style for an extra-large label
     * that M3 doesn’t natively define.
     */
    val OutfitLabelExtraLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    )
}


