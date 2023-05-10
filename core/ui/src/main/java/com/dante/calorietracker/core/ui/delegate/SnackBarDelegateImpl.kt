package com.dante.calorietracker.core.ui.delegate

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

class SnackBarDelegateImpl(
    private val snackBarHostState: SnackbarHostState? = null,
    private val coroutineScope: CoroutineScope? = null
) : SnackBarDelegate {
    override fun showSnackBarAsync(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration
    ): Deferred<SnackbarResult?>? =
        coroutineScope?.async {
            snackBarHostState?.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                duration = duration
            )
        }
}

val LocalSnackBarDelegate = staticCompositionLocalOf<SnackBarDelegate> { SnackBarDelegateImpl() }
