package dev.gaddal.scribbledash.home.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import dev.gaddal.scribbledash.core.presentation.designsystem.ScribbleDashTheme
import dev.gaddal.scribbledash.core.presentation.designsystem.colors.AppColors
import dev.gaddal.scribbledash.core.presentation.ui.navigation.HomeNavItem
import dev.gaddal.scribbledash.core.presentation.ui.navigation.NavItemInfo
import dev.gaddal.scribbledash.core.presentation.ui.navigation.homeNavItemsInfo
import dev.gaddal.scribbledash.home.presentation.HomeAction

@Composable
fun HomeBottomBar(
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier,
    navItems: List<NavItemInfo>,
    withLabel: Boolean = true,
) {
    val currentDestination = LocalCurrentDestination.current

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        navItems.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.route::class) == true
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.iconResId),
                        contentDescription = item.title.asString(),
                    )
                },
                label = {
                    if (withLabel) Text(text = item.title.asString())
                },
                selected = isSelected,
                onClick = {
                    when (item.route) {
                        is HomeNavItem.Home -> onAction(HomeAction.OnHomeClick)
                        is HomeNavItem.Chart -> onAction(HomeAction.OnChartClick)
                        else -> {}
                    }
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = AppColors.SurfaceLow,
                )
            )
        }
    }
}

@Preview
@Composable
fun MaktabatiBottomBarPreview() {
    ScribbleDashTheme {
        HomeBottomBar(
            onAction = {},
            navItems = homeNavItemsInfo,
            withLabel = false
        )
    }
}