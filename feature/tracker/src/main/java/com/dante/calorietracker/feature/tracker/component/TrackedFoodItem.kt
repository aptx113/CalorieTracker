package com.dante.calorietracker.feature.tracker.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.ui.R
import com.dante.calorietracker.core.ui.component.CalorieTrackerIconButton
import com.dante.calorietracker.core.ui.component.NutrientInfo
import com.dante.calorietracker.core.ui.icon.CalorieTrackerIcons.Close
import com.dante.calorietracker.core.ui.unit.LocalDimens

@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimens = LocalDimens.current
    val context = LocalContext.current
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(dimens.space4)
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(5.dp))
            .padding(end = dimens.space16)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context).data(trackedFood.imageUrl)
                    .crossfade(true)
                    .error(R.drawable.ic_burger)
                    .fallback(R.drawable.ic_burger),
            ),
            contentDescription = trackedFood.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
        )
        Spacer(modifier = Modifier.width(dimens.space16))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = trackedFood.name,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(dimens.space4))
            Text(
                text = stringResource(
                    id = R.string.nutrient_info,
                    trackedFood.amount,
                    trackedFood.calories
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.width(dimens.space16))
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            CalorieTrackerIconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.End),
            ) {
                Icon(
                    imageVector = Close,
                    contentDescription = stringResource(id = R.string.delete),
                )
            }
            Spacer(modifier = Modifier.height(dimens.space4))
            Row(verticalAlignment = Alignment.CenterVertically) {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = trackedFood.carbs,
                    unit = stringResource(
                        id = R.string.grams
                    ),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(dimens.space8))
                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = trackedFood.protein,
                    unit = stringResource(
                        id = R.string.grams
                    ),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(dimens.space8))
                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = trackedFood.fat,
                    unit = stringResource(
                        id = R.string.grams
                    ),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}
