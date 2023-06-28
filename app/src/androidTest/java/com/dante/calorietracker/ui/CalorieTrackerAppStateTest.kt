package com.dante.calorietracker.ui

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import kotlinx.coroutines.test.TestScope
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class CalorieTrackerAppStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: CalorieTrackerAppState

    @Test
    fun calorieTrackerAppState_currentDestination() {
        var currentDestination: String? = null

        composeTestRule.setContent {
            val navController = rememberTestNavController()
            state = remember(key1 = navController) {
                CalorieTrackerAppState(
                    navController = navController,
                    coroutineScope = TestScope().backgroundScope,
                    userDataRepository = TestUserDataRepository(),
                )
            }
            currentDestination = state.currentDestination?.route
            LaunchedEffect(Unit) {
                navController.setCurrentDestination("b")
            }
        }
        assertEquals("b", currentDestination)
    }
}

@Composable
private fun rememberTestNavController(): TestNavHostController {
    val context = LocalContext.current
    val navController = remember {
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = "a") {
                composable("a") {}
                composable("b") {}
                composable("c") {}
            }
        }
    }
    return navController
}
