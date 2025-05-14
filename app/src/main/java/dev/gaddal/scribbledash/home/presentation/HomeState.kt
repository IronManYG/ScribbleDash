package dev.gaddal.scribbledash.home.presentation

import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem

data class HomeState(
    val isLoading: Boolean = false,
    val currentDestination: HomeNavItem = HomeNavItem.HomeGameMode,

    // Statistics
    val highestAccuracySpeedDraw: Float = 0f,
    val mostDrawingsCompletedSpeedDraw: Int = 0,
    val highestAccuracyEndlessMode: Float = 0f,
    val mostDrawingsCompletedEndlessMode: Int = 0,
)
