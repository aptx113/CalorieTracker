package com.dante.calorietracker.core.network

import com.dante.calorietracker.core.network.model.NetworkProduct

interface CalorieTrackerDataSource {
    suspend fun searchFood(query: String, page: Int, pageSize: Int): List<NetworkProduct>
}
