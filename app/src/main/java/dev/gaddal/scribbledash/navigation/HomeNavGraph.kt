package dev.gaddal.scribbledash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import dev.gaddal.scribbledash.core.presentation.ui.navigation.MainScreen
import dev.gaddal.scribbledash.gameModes.endless.presentation.EndlessRoot
import dev.gaddal.scribbledash.gameModes.oneRoundWonder.presentation.OneRoundWonderRoot
import dev.gaddal.scribbledash.gameModes.speedDraw.presentation.SpeedDrawRoot
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
                        GameMode.SpeedDraw -> navController.navigate(HomeNavItem.SpeedDraw)
                        GameMode.EndlessMode -> navController.navigate(HomeNavItem.EndlessMode)
                    }
                }
            )
        }
        composable<HomeNavItem.OneRoundWonder> {
            OneRoundWonderRoot(
                onCloseClick = { navController.popBackStack() },
                onLevelClick = {}
            )
        }
        composable<HomeNavItem.SpeedDraw> {
            SpeedDrawRoot(
                onCloseClick = { navController.popBackStack() }
            )
        }
        composable<HomeNavItem.EndlessMode> {
            EndlessRoot(
                onCloseClick = { navController.popBackStack() }
            )
        }
    }
}