package dev.gaddal.scribbledash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import dev.gaddal.scribbledash.core.presentation.ui.navigation.MainScreen
import dev.gaddal.scribbledash.home.presentation.HomeRoot

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation<MainScreen.Home>(
        startDestination = HomeNavItem.Home,
    ) {
        composable<HomeNavItem.Home> {
            HomeRoot(
                onGameModeClick = { gameMode ->
                    when (gameMode) {
                        GameMode.OneRoundWonder -> navController.navigate(HomeNavItem.OneRoundWonder)
                    }
                }
            )
        }
        composable<HomeNavItem.OneRoundWonder> {

        }
    }
}