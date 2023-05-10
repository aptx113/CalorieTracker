package com.dante.calorietracker.core.ui.delegate

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.Deferred

interface SnackBarDelegate {
    fun showSnackBarAsync(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): Deferred<SnackbarResult?>?
}
