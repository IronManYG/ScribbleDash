package dev.gaddal.scribbledash.core.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed class HomeNavItem : Screen() {
    @Serializable
    data object Home : HomeNavItem()

    @Serializable
    data object HomeGameMode : HomeNavItem() // only for button nav

    @Serializable
    data object Chart : HomeNavItem() // only for button nav

    // Game modes
    @Serializable
    data object OneRoundWonder : HomeNavItem()

    @Serializable
    data object SpeedDraw : HomeNavItem()

    @Serializable
    data object EndlessMode : HomeNavItem()
}