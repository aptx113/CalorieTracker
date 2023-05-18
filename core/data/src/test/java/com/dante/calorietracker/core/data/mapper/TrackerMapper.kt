package com.dante.calorietracker.core.data.mapper

import com.dante.calorietracker.core.database.model.TrackedFoodEntity
import com.dante.calorietracker.core.model.MealType
import com.dante.calorietracker.core.model.TrackedFood
import com.dante.calorietracker.core.network.model.NetworkNutriments
import com.dante.calorietracker.core.network.model.NetworkProduct
import kotlinx.datetime.LocalDate
import org.junit.Test
import kotlin.test.assertEquals

class TrackerMapper {

    @Test
    fun network_product_can_be_mapped_to_trackable_food() {
        val networkProduct = NetworkProduct(
            imageFrontThumbUrl = "https://static.openfoodfacts.org/images/products/541/118/811/0485/front_fr.3.400.jpg",
            nutriments = NetworkNutriments(
                carbohydrates100g = 0.0,
                proteins100g = 0.0,
                fat100g = 0.0,
                energyKcal100g = 0.0
            ),
            productName = "Coca Cola"
        )
        val trackableFood = networkProduct.asTrackableFood()

        assertEquals("Coca Cola", trackableFood.name)
        assertEquals(
            "https://static.openfoodfacts.org/images/products/541/118/811/0485/front_fr.3.400.jpg",
            trackableFood.imageUrl
        )
        assertEquals(0, trackableFood.caloriesPer100g)
        assertEquals(0, trackableFood.carbsPer100g)
        assertEquals(0, trackableFood.proteinsPer100g)
        assertEquals(0, trackableFood.fatsPer100g)
    }

    @Test
    fun tracked_foodEntity_can_be_mapped_to_trackedFood() {
        val trackedFoodEntity = TrackedFoodEntity(
            id = 1,
            name = "Coca Cola",
            imageUrl = "https://static.openfoodfacts.org/images/products/541/118/811/0485/front_fr.3.400.jpg",
            calories = 0,
            carbs = 0,
            proteins = 0,
            fats = 0,
            type = "Drink",
            amount = 0,
            dayOfMonth = 1,
            month = 9,
            year = 2021,
        )
        val trackedFood = trackedFoodEntity.asTrackedFood()

        assertEquals("Coca Cola", trackedFood.name)
        assertEquals(
            "https://static.openfoodfacts.org/images/products/541/118/811/0485/front_fr.3.400.jpg",
            trackedFood.imageUrl
        )
        assertEquals(0, trackedFood.calories)
        assertEquals(0, trackedFood.carbs)
        assertEquals(0, trackedFood.proteins)
        assertEquals(0, trackedFood.fats)
        assertEquals("breakfast", trackedFood.mealType.name)
        assertEquals(0, trackedFood.amount)
        assertEquals(1, trackedFood.date.dayOfMonth)
        assertEquals(9, trackedFood.date.monthNumber)
        assertEquals(2021, trackedFood.date.year)
    }

    @Test
    fun trackedFood_can_be_mapped_to_tracked_foodEntity() {
        val trackedFood = TrackedFood(
            id = 1,
            name = "Coca Cola",
            imageUrl = "https://static.openfoodfacts.org/images/products/541/118/811/0485/front_fr.3.400.jpg",
            calories = 0,
            carbs = 0,
            proteins = 0,
            fats = 0,
            mealType = MealType.Lunch,
            amount = 0,
            date = LocalDate(2021, 9, 1),
        )
        val trackedFoodEntity = trackedFood.asTrackedFoodEntity()

        assertEquals("Coca Cola", trackedFoodEntity.name)
        assertEquals(
            "https://static.openfoodfacts.org/images/products/541/118/811/0485/front_fr.3.400.jpg",
            trackedFoodEntity.imageUrl
        )
        assertEquals(0, trackedFoodEntity.calories)
        assertEquals(0, trackedFoodEntity.carbs)
        assertEquals(0, trackedFoodEntity.proteins)
        assertEquals(0, trackedFoodEntity.fats)
        assertEquals("lunch", trackedFoodEntity.type)
        assertEquals(0, trackedFoodEntity.amount)
        assertEquals(1, trackedFoodEntity.dayOfMonth)
        assertEquals(9, trackedFoodEntity.month)
        assertEquals(2021, trackedFoodEntity.year)
    }
}
