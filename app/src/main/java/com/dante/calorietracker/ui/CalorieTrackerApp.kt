package com.dante.calorietracker.ui

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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.delegate.LocalSnackBarDelegate
import com.dante.calorietracker.core.ui.delegate.SnackBarDelegateImpl
import com.dante.calorietracker.navigation.CalorieTrackerNavHost
import com.dante.calorietracker.navigation.TopLevelDestination


@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CalorieTrackerApp(
    windowSizeClass: WindowSizeClass,
    appState: CalorieTrackerAppState = rememberCalorieTrackerAppState(
        windowSizeClass = windowSizeClass
    )
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val defaultSnackBarDelegateImpl = SnackBarDelegateImpl(
        snackBarHostState = snackBarHostState,
        coroutineScope = rememberCoroutineScope()
    )
    Background {
        Scaffold(
            modifier = Modifier.semantics {
                testTagsAsResourceId = true
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
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
                        .padding(padding)
                ) {
                    CompositionLocalProvider(LocalSnackBarDelegate provides defaultSnackBarDelegateImpl) {
                        CalorieTrackerNavHost(appState)
                    }
                }
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
