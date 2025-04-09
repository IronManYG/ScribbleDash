package dev.gaddal.scribbledash.core.presentation.ui.navigation

import dev.gaddal.scribbledash.R
import dev.gaddal.scribbledash.core.presentation.ui.UiText

val homeNavItemsInfo = listOf(
    NavItemInfo(
        route = HomeNavItem.Chart,
        title = UiText.StringResource(R.string.chart),
        iconResId = R.drawable.chart
    ),
    NavItemInfo(
        route = HomeNavItem.HomeGameMode,
        title = UiText.StringResource(R.string.home),
        iconResId = R.drawable.home
    ),
)

val gameModeNavItemsInfo = listOf(
    NavItemInfo(
        route = HomeNavItem.OneRoundWonder,
        title = UiText.StringResource(R.string.one_round_wonder),
        iconResId = R.drawable.one_round_wonder
    ),
)