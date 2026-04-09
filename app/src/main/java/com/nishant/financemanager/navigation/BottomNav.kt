package com.nishant.financemanager.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.nishant.financemanager.ui.screens.HomeScreen
import com.nishant.financemanager.ui.screens.CategoryScreen
import com.nishant.financemanager.ui.screens.SummaryScreen
import com.nishant.financemanager.viewmodel.FinanceViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNav(
    viewModel: FinanceViewModel,
    darkMode: Boolean,
    onToggleTheme: () -> Unit
) {

    var selected by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = null)
                    },
                    label = { Text("Home") },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF063770),
                        selectedTextColor = Color(0xFF063770),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    icon = {
                        Icon(Icons.Default.List, contentDescription = null)
                    },
                    label = { Text("Categories") },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF063770),
                        selectedTextColor = Color(0xFF063770),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    icon = {
                        Icon(Icons.Default.PieChart, contentDescription = null)
                    },
                    label = { Text("Summary") },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF063770),
                        selectedTextColor = Color(0xFF063770),
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { padding ->

        AnimatedContent(
            targetState = selected,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(300),
                    initialOffsetX = { it }
                ) + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(300),
                            targetOffsetX = { -it }
                        ) + fadeOut()
            }
        ) { screen ->

            when (screen) {
                0 -> HomeScreen(viewModel, darkMode , onToggleTheme)
                1 -> CategoryScreen(viewModel)
                2 -> SummaryScreen(viewModel)
            }
        }
    }
}