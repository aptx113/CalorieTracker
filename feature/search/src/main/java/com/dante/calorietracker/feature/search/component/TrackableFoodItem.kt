package com.dante.calorietracker.feature.search.component

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dante.calorietracker.core.data.mockTrackableFood
import com.dante.calorietracker.core.model.TrackableFood
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.CalorieTrackerIconButton
import com.dante.calorietracker.core.ui.component.NutrientInfo
import com.dante.calorietracker.core.ui.component.ThemePreviews
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons.Check
import com.dante.calorietracker.core.ui.theme.CalorieTrackerTheme
import com.dante.calorietracker.core.ui.unit.Dimensions
import com.dante.calorietracker.core.ui.unit.LocalDimens
import com.dante.calorietracker.feature.search.TrackableFoodUiState

@Composable
fun TrackableFoodItem(
    trackableFoodUiState: TrackableFoodUiState,
    onClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    onTrack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val food = trackableFoodUiState.food
    val dimens = LocalDimens.current
    val context = LocalContext.current
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(dimens.space4)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(end = dimens.space16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                FoodImage(food = food, context = context)
                Spacer(modifier = Modifier.width(dimens.space16))
                Column(modifier = Modifier.align(CenterVertically)) {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(dimens.space8))
                    Text(
                        text = stringResource(id = R.string.kcal_per_100g, food.caloriePer100g),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            NutrientContent(food = food)
        }
        AnimatedVisibility(visible = trackableFoodUiState.isExpanded) {
            InputAmountArea(
                dimens = dimens,
                trackableFoodUiState = trackableFoodUiState,
                onAmountChange = onAmountChange,
                onTrack = onTrack
            )
        }
    }
}

@Composable
private fun InputAmountArea(
    dimens: Dimensions,
    trackableFoodUiState: TrackableFoodUiState,
    onAmountChange: (String) -> Unit,
    onTrack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.space16),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = CenterVertically
    ) {
        FoodAmountTextField(
            value = trackableFoodUiState.amount,
            trackableFoodUiState = trackableFoodUiState,
            onValueChange = onAmountChange,
            onTrack = onTrack,
            modifier = Modifier
                .padding(dimens.space16)
        )
        CalorieTrackerIconButton(
            onClick = onTrack,
            enabled = trackableFoodUiState.amount.isNotEmpty()
        ) {
            Icon(
                imageVector = Check,
                contentDescription = stringResource(id = R.string.track),
            )
        }
    }

}

@Composable
private fun FoodImage(food: TrackableFood, context: Context) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(food.imageUrl)
                .crossfade(true)
                .error(R.drawable.ic_burger)
                .fallback(R.drawable.ic_burger)
                .build()
        ),
        contentDescription = food.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(5.dp))
    )
}

@Composable
private fun NutrientContent(food: TrackableFood) {
    val dimens = LocalDimens.current
    Row {
        NutrientInfo(
            name = stringResource(id = R.string.carbs),
            amount = food.carbsPer100g,
            unit = stringResource(id = R.string.grams),
            amountTextSize = 16.sp,
            unitTextSize = 12.sp,
            nameTextStyle = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.width(dimens.space8))
        NutrientInfo(
            name = stringResource(id = R.string.protein),
            amount = food.proteinPer100g,
            unit = stringResource(id = R.string.grams),
            amountTextSize = 16.sp,
            unitTextSize = 12.sp,
            nameTextStyle = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.width(dimens.space8))
        NutrientInfo(
            name = stringResource(id = R.string.fat),
            amount = food.fatPer100g,
            unit = stringResource(id = R.string.grams),
            amountTextSize = 16.sp,
            unitTextSize = 12.sp,
            nameTextStyle = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun FoodAmountTextField(
    value: String,
    trackableFoodUiState: TrackableFoodUiState,
    onValueChange: (String) -> Unit,
    onTrack: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardActions = KeyboardActions(
            onDone = {
                onTrack()
                defaultKeyboardAction(ImeAction.Done)
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = if (trackableFoodUiState.amount.isNotBlank()) {
                ImeAction.Done
            } else {
                ImeAction.Default
            },
            keyboardType = KeyboardType.Decimal
        ),
        isError = isError,
        singleLine = true,
        shape = RoundedCornerShape(5.dp),
        colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.onSurface),
        suffix = {
            Text(
                text = stringResource(id = R.string.grams),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}

@ThemePreviews
@Composable
private fun TrackableFoodItemPrev() {
    CalorieTrackerTheme {
        TrackableFoodItem(
            trackableFoodUiState = TrackableFoodUiState(
                food = TrackableFood(
                    name = "pasta",
                    imageUrl = "https://www.lemonblossoms.com/wp-content/uploads/2022/03/Butter-Noodles-S5.jpg",
                    caloriePer100g = 300,
                    carbsPer100g = 40,
                    proteinPer100g = 10,
                    fatPer100g = 10
                ), isExpanded = true,
                amount = "1"
            ),
            onClick = {},
            onAmountChange = { /*TODO*/ },
            onTrack = { /*TODO*/ }
        )
    }
}

@ThemePreviews
@Composable
private fun InputAmountAreaPrev() {
    CalorieTrackerTheme {
        val dimens = LocalDimens.current
        InputAmountArea(
            dimens = dimens,
            trackableFoodUiState = TrackableFoodUiState(
                food = mockTrackableFood,
                isExpanded = true,
                amount = "1"
            ),
            onAmountChange = {},
            onTrack = {}
        )
    }
}

@ThemePreviews
@Composable
private fun NutrientContentPrev() {
    CalorieTrackerTheme {
        NutrientContent(
            mockTrackableFood
        )
    }
}
