package dev.gaddal.scribbledash.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.gaddal.scribbledash.core.domain.gameMode.GameMode
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import dev.gaddal.scribbledash.core.presentation.ui.navigation.MainScreen
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Endless Mode")
            }
        }
    }
}