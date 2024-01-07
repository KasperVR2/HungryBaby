package com.example.hungrybaby

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.hungrybaby.ui.HungryBabyApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    // Navigation tests will fail on first run because the app is not yet initialized!!!

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            HungryBabyApp(navController = navController)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Hungry Baby")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToAbout() {
        composeTestRule
            .onNodeWithContentDescription("navigate to about page")
            .performClick()
        composeTestRule
            .onNodeWithText("About")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToNews() {
        composeTestRule
            .onNodeWithContentDescription("navigate to news page")
            .performClick()
        composeTestRule
            .onNodeWithText("News")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToSettings() {
        composeTestRule
            .onNodeWithContentDescription("navigate to settings page")
            .performClick()
        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()
    }

    @Test
    fun clickAddFood() {
        composeTestRule
            .onNodeWithContentDescription("Bottle")
            .performClick()
        composeTestRule
            .onNodeWithText("Confirm")
            .assertIsDisplayed()
    }

    @Test
    fun clickDeleteAllFood() {
        composeTestRule
            .onNodeWithContentDescription("navigate to settings page")
            .performClick()
        composeTestRule
            .onNodeWithTag("deleteAllButton")
            .performClick()
        composeTestRule
            .onNodeWithText("Confirm")
            .assertIsDisplayed()
    }

    @Test
    fun clickReset() {
        composeTestRule
            .onNodeWithContentDescription("navigate to settings page")
            .performClick()
        composeTestRule
            .onNodeWithTag("resetButton")
            .performClick()
        composeTestRule
            .onNodeWithText("Save")
            .assertIsDisplayed()
    }

    @Test
    fun addFoodWithoutVolumeGivesWarning() {
        composeTestRule
            .onNodeWithContentDescription("Bottle")
            .performClick()
        composeTestRule
            .onNodeWithText("Confirm")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Confirm")
            .performClick()
        composeTestRule
            .onNodeWithTag("warning")
            .assertIsDisplayed()
    }
}
