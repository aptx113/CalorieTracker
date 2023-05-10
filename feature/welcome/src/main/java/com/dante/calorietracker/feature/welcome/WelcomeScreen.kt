package com.dante.calorietracker.feature.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.Background
import com.dante.calorietracker.core.ui.component.AppButton
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
internal fun WelcomeRoute(onAgeNavigated: () -> Unit, modifier: Modifier = Modifier) {
    WelcomeScreen(onAgeNavigated, modifier)
}

@Composable
internal fun WelcomeScreen(onAgeNavigated: () -> Unit, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.space16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(spacing.space16))
        AppButton(
            onClick = onAgeNavigated,
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@ThemePreviews
@Composable
fun WelcomeScreenPrev() {
    CalorieTrackerTheme {
        Background {
            WelcomeScreen({})
        }
    }
}
