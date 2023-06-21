package com.dante.calorietracker.feature.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.CalorieTrackerIconButton
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons.ArrowBack
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalDimens

@Composable
private fun SearchToolbar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String = "",
    onSearchTriggered: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        CalorieTrackerIconButton(onClick = onBackClick) {
            Icon(
                imageVector = ArrowBack,
                contentDescription = stringResource(id = R.string.back)
            )
        }
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            searchQuery = searchQuery,
            onSearchTriggered = onSearchTriggered
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchTextField(
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    onSearchTriggered: (String) -> Unit
) {
    val dimens = LocalDimens.current
    val focusRequester = remember {
        FocusRequester()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        value = searchQuery,
        onValueChange = {
            if (!it.contains("\n")) {
                onSearchQueryChanged(it)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = CalorieTrackerIcons.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                CalorieTrackerIconButton(onClick = { onSearchQueryChanged("") }) {
                    Icon(
                        imageVector = CalorieTrackerIcons.Close,
                        contentDescription = stringResource(id = R.string.clear_search_text_content_desc)
                    )
                }
            }
        },
        shape = RoundedCornerShape(dimens.space16),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchExplicitlyTriggered() }),
        maxLines = 1,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.space16)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField")
    )
}

@ThemePreviews
@Composable
fun SearchToolbarPrev() {
    CalorieTrackerTheme {
        SearchToolbar(
            onBackClick = {},
            onSearchQueryChanged = {},
            onSearchTriggered = {}
        )
    }
}
