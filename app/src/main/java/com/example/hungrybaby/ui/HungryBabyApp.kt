package com.example.hungrybaby.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hungrybaby.ui.home.Home
import com.example.hungrybaby.ui.register.Register
import com.example.hungrybaby.ui.shared.Scaff
import com.example.hungrybaby.ui.shared.ScreenNames

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HungryBabyApp(
    babyData: String?,
    navController: NavHostController = rememberNavController(),
) {
    // Navigation stuff
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit = {
        navController.popBackStack(
            ScreenNames.Start.name,
            inclusive = false,
        )
    }
    val goToHomeScreen = { navController.navigate(ScreenNames.Start.name) }
    val currentScreenTitle =
        ScreenNames.valueOf(
            backStackEntry?.destination?.route ?: ScreenNames.Start.name,
        ).title

    val startDest =
        if (babyData.isNullOrEmpty()) {
            ScreenNames.Register.name
        } else {
            ScreenNames.Start.name
        }

    NavHost(
        navController = navController,
        startDestination = startDest,
    ) {
        composable(ScreenNames.Start.name) {
            Scaff({ Home() })
        }
        composable(ScreenNames.Register.name) {
            Register(goToHomeScreen)
        }
    }
}
