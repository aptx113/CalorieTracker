package com.dante.calorietracker.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dante.calorietracker.MainUiState
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.delegate.LocalSnackBarDelegate
import com.dante.calorietracker.core.ui.delegate.SnackBarDelegateImpl
import com.dante.calorietracker.feature.tracker.navigation.trackerRoute
import com.dante.calorietracker.navigation.CalorieTrackerNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CalorieTrackerApp(
    appState: CalorieTrackerAppState = rememberCalorieTrackerAppState(),
    uiState: MainUiState
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val defaultSnackBarDelegateImpl = SnackBarDelegateImpl(
        snackBarHostState = snackBarHostState,
        coroutineScope = rememberCoroutineScope()
    )
    Background {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
        val statusBarColor = if (navBackStackEntry?.destination?.route == trackerRoute) {
            MaterialTheme.colorScheme.primary
        } else Color.Transparent

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = useDarkIcons
            )
            onDispose { }
        }
        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(top = 0.dp),
        ) { padding ->
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    )
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    CompositionLocalProvider(LocalSnackBarDelegate provides defaultSnackBarDelegateImpl) {
                        if (uiState is MainUiState.StartDestination) {
                            CalorieTrackerNavHost(appState, startDestination = uiState.destination)
                        }
                    }
                }
            }
        }
    }
}
