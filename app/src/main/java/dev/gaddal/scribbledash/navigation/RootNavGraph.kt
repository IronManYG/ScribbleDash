package dev.gaddal.scribbledash.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.gaddal.scribbledash.core.presentation.ui.navigation.MainScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.Home
    ) {
        homeNavGraph(navController)
    }
}