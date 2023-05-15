package com.dante.calorietracker.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.dante.calorietracker.core.ui.unit.LocalSpacing
import com.dante.calorietracker.core.ui.utils.TextFieldState

@Composable
fun UnitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    textStyle: TextStyle = TextStyle(color = MaterialTheme.colorScheme.secondary, fontSize = 36.sp)
) {
    val spacing = LocalSpacing.current
    Row(horizontalArrangement = Arrangement.Center) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            modifier = modifier
                .width(IntrinsicSize.Min)
                .alignBy(LastBaseline)
        )
        Spacer(modifier = Modifier.width(spacing.space8))
        Text(text = unit, modifier = Modifier.alignBy(LastBaseline))
    }
}

@Composable
fun UnitTextField(
    label: String,
    textFieldState: TextFieldState,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    suffix: String,
    modifier: Modifier = Modifier,
) {
    CalorieTrackerTextField(
        label = label,
        textFieldState = textFieldState,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        suffix = suffix,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieTrackerTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    suffix: String = "",
    supportingText: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        },
        suffix = {
            Text(text = suffix, style = MaterialTheme.typography.bodyMedium)
        },
        isError = isError,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
fun CalorieTrackerTextField(
    label: String,
    textFieldState: TextFieldState,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    suffix: String = ""
) {
    CalorieTrackerTextField(
        value = textFieldState.text,
        label = label,
        onValueChange = {
            textFieldState.text = it
            textFieldState.enableShowError()
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                textFieldState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    textFieldState.enableShowError()
                }
            },
        isError = textFieldState.showErrors(),
        suffix = suffix,
        supportingText = {
            textFieldState.getError()?.let { error -> TextFieldError(textError = error) }
        }
    )
}

@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(LocalSpacing.current.space8))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}



