package dev.gaddal.scribbledash.core.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed class MainScreen : Screen() {
    @Serializable
    data object Home : MainScreen()
}