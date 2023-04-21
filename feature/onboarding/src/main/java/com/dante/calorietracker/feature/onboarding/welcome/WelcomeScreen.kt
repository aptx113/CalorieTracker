package com.dante.calorietracker.feature.onboarding.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.AppBackground
import com.dante.calorietracker.core.ui.component.AppButton
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalSpacing

@Composable
fun WelcomeScreen() {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(spacing.space16))
        AppButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = stringResource(id = R.string.next))
        }
    }
}

@ThemePreviews
@Composable
fun WelcomeScreenPrev() {
    CalorieTrackerTheme {
        AppBackground {
            WelcomeScreen()
        }
    }
}
