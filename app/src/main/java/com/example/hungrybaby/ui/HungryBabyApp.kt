package com.example.hungrybaby.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hungrybaby.ui.about.About
import com.example.hungrybaby.ui.home.Home
import com.example.hungrybaby.ui.register.Register
import com.example.hungrybaby.ui.settings.Settings
import com.example.hungrybaby.ui.shared.Scaff
import com.example.hungrybaby.ui.shared.ScreenNames
import com.example.hungrybaby.ui.shared.StartData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HungryBabyApp(navController: NavHostController = rememberNavController()) {
    // Getting baby information from the datastore
    val data = StartData(LocalContext.current)
    val babyData = runBlocking { data.getBabyData.firstOrNull() }
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
    val goToAbout: () -> Unit = { navController.navigate(ScreenNames.About.name) }
    val goToSettings: () -> Unit = { navController.navigate(ScreenNames.Settings.name) }
    val goToRegister: () -> Unit = { navController.navigate(ScreenNames.Register.name) }
    // Go home without adding to the backstack
    val goToHomeScreen = {
        navController.navigate(ScreenNames.Start.name) {
            popUpTo(ScreenNames.Register.name) {
                inclusive = true
                saveState = true
            }
        }
    }
    // Current screen title
    val currentScreenTitle =
        ScreenNames.valueOf(
            backStackEntry?.destination?.route ?: ScreenNames.Start.name,
        ).title

    // Change starting destination based on whether there is already baby data
    val startDest =
        if (babyData.isNullOrEmpty()) {
            ScreenNames.RegisterFirst.name
        } else {
            ScreenNames.Start.name
        }

    @Composable
    fun ScaffoldBuilder(body: @Composable () -> Unit) {
        Scaff(
            canNavigateBack = canNavigateBack,
            navigateUp = navigateUp,
            currentScreenTitle = currentScreenTitle,
            goHome = goHome,
            goToAbout = goToAbout,
            goToSettings = goToSettings,
            body = body,
        )
    }

    NavHost(
        navController = navController,
        startDestination = startDest,
    ) {
        composable(ScreenNames.Start.name) {
            ScaffoldBuilder(body = { Home() })
        }
        composable(ScreenNames.About.name) {
            ScaffoldBuilder(body = { About() })
        }
        composable(ScreenNames.Settings.name) {
            ScaffoldBuilder(body = { Settings(goToRegister) })
        }
        composable(ScreenNames.RegisterFirst.name) {
            Register(goToHomeScreen, navigateUp)
        }
        composable(ScreenNames.Register.name) {
            Register(goToHomeScreen, navigateUp, firstTime = false)
        }
    }
}
