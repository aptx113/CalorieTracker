package com.dante.calorietracker.core.data.mapper

import com.dante.calorietracker.core.model.TrackableFood
import com.dante.calorietracker.core.network.model.NetworkProduct
import kotlin.math.roundToInt

fun NetworkProduct.toTrackableFood(): TrackableFood? {
    val carbsPer100g = nutriments.carbohydrates100g.roundToInt()
    val proteinsPer100g = nutriments.proteins100g.roundToInt()
    val fatsPer100g = nutriments.fat100g.roundToInt()
    val caloriesPer100g = nutriments.energyKcal100g.roundToInt()
    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageFrontThumbUrl,
        caloriePer100g = caloriesPer100g,
        carbsPer100g = carbsPer100g,
        proteinsPer100g = proteinsPer100g,
        fatsPer100g = fatsPer100g
    )
}
