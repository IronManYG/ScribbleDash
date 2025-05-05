package dev.gaddal.scribbledash.core.presentation.designsystem.colors

import androidx.compose.ui.graphics.Color

object AppColors {
    // Brand
    val PrimaryBlue = Color(0xFF238CFF)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnPrimary40 = OnPrimary.copy(alpha = 0.40f)

    val SecondaryPurple = Color(0xFFAB5CFA)
    val TertiaryOrange = Color(0xFFFA852C)

    val ErrorRed = Color(0xFFEF1242)
    val SuccessGreen = Color(0xFF0DD280)

    // Scheme
    val Background = Color(0xFFFEFAF6)
    val BackgroundGradient = listOf(Color(0xFFFEFAF6), Color(0xFFFFF1E2))

    val OnBackground = Color(0xFF514437)
    val OnBackgroundVariant = Color(0xFF7F7163)

    val SurfaceHigh = Color(0xFFFFFFFF)
    val Surface80 = SurfaceHigh.copy(alpha = 0.80f)
    val SurfaceLow = Color(0xFFEEE7E0)
    val SurfaceLowest = Color(0xFFE1D5CA)

    val OnSurface = Color(0xFFA5978A)
    val OnSurfaceVariant = Color(0xFFF6F1EC)

    val Shadow = Color(0xFF726558)
    val Shadow_SL12 = Shadow.copy(alpha = 0.12f) // Shadow / opacity - 0.08
}