package com.dante.calorietracker.feature.tracker.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.dante.calorietracker.core.TimeZoneBroadcastReceiver
import com.dante.calorietracker.core.ui.R
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.todayIn
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun LocalDate.parseDateText(): String {
    var zoneId by remember {
        mutableStateOf(ZoneId.systemDefault())
    }
    val context = LocalContext.current

    DisposableEffect(context) {
        val receiver =
            TimeZoneBroadcastReceiver(onTimeZoneChanged = { zoneId = ZoneId.systemDefault() })
        receiver.register(context)
        onDispose {
            receiver.unregister(context)
        }
    }
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return when (this) {
        today -> stringResource(id = R.string.today)
        today.minus(1, DateTimeUnit.DAY) -> stringResource(id = R.string.yesterday)
        today.plus(1, DateTimeUnit.DAY) -> stringResource(
            id = R.string.tomorrow
        )

        else -> DateTimeFormatter.ofPattern("yyyy MMM d").withZone(zoneId)
            .format(this.toJavaLocalDate())
    }
}
