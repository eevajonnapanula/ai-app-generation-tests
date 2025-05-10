package com.eevajonna.junietestapplication.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eevajonna.junietestapplication.R
import com.eevajonna.junietestapplication.ui.navigation.Screen

/**
 * Main app scaffold with bottom navigation.
 */
@Composable
fun AppScaffold(
    navController: NavHostController,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

/**
 * Bottom navigation bar for the app.
 */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem(
            route = Screen.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            labelResId = R.string.home
        ),
        NavigationItem(
            route = Screen.YarnList.route,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            labelResId = R.string.yarn
        ),
        NavigationItem(
            route = Screen.NeedlesList.route,
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            labelResId = R.string.needles
        )
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    NavigationBar {
        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            val label = stringResource(id = item.labelResId)
            
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = label,
                        modifier = Modifier.semantics {
                            contentDescription = label
                        }
                    )
                },
                label = { Text(text = label) },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

/**
 * Data class for navigation items.
 */
data class NavigationItem(
    val route: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val labelResId: Int
)