package com.dante.calorietracker.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dante.calorietracker.core.model.SearchArgs
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.CalorieTrackerIconButton
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons.ArrowBack
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.LocalDimens

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    searchArgs: SearchArgs,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResultUiState by viewModel.searchResultUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    SearchScreen(
        modifier = modifier,
        searchResultUiState = searchResultUiState,
        mealType = searchArgs.mealType,
        dayOfMonth = searchArgs.dayOfMonth,
        month = searchArgs.month,
        year = searchArgs.year,
        onBackClick = onBackClick,
        onSearchQueryChanged = viewModel::onQueryChanged,
        searchQuery = searchQuery,
        onSearchTriggered = viewModel::onSearchTriggered,
    )
}

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    searchResultUiState: SearchResultUiState = SearchResultUiState.Loading,
    mealType: String = "",
    dayOfMonth: Int = 0,
    month: Int = 0,
    year: Int = 0,
    onBackClick: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    searchQuery: String = "",
    onSearchTriggered: (String) -> Unit = {}
) {

    val dimens = LocalDimens.current
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
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
            Text(
                text = stringResource(id = R.string.add_meal, mealType),
                style = MaterialTheme.typography.titleLarge,
            )
        }

        Spacer(modifier = Modifier.height(dimens.space16))
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            searchQuery = searchQuery,
            onSearchTriggered = onSearchTriggered
        )
    }
}

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
        label = {
            Text(text = stringResource(id = R.string.search))
        },
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
            .padding(horizontal = dimens.space16)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(dimens.space16))
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

@ThemePreviews
@Composable
fun SearchPrev() {
    CalorieTrackerTheme {
        SearchScreen()
    }
}
