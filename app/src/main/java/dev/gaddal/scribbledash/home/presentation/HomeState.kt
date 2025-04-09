package dev.gaddal.scribbledash.home.presentation

import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem

data class HomeState(
    val isLoading: Boolean = false,
    val currentDestination: HomeNavItem = HomeNavItem.HomeGameMode,
)
