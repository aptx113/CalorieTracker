package com.dante.calorietracker.core.data.testdoubles

import com.dante.calorietracker.core.network.CalorieTrackerDataSource
import com.dante.calorietracker.core.network.model.NetworkNutriments
import com.dante.calorietracker.core.network.model.NetworkProduct

class TestTrackerDataSource : CalorieTrackerDataSource {
    override suspend fun searchFood(query: String, page: Int, pageSize: Int): List<NetworkProduct> {
        return listOf(
            NetworkProduct(
                productName = "Pasta",
                imageFrontThumbUrl = "https",
                nutriments = NetworkNutriments(1.0, 1.0, 1.0, 1.0)
            ),
            NetworkProduct(
                productName = "Pizza",
                imageFrontThumbUrl = "https://",
                nutriments = NetworkNutriments(2.0, 1.0, 1.0, 1.0)
            )
        )
    }
}
