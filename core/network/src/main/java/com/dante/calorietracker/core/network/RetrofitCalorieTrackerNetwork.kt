package com.dante.calorietracker.core.network

import com.dante.calorietracker.core.network.model.NetworkProduct
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://us.openfoodfacts.org/"

@Singleton
class RetrofitCalorieTrackerNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory
) : CalorieTrackerDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create<CalorieTrackerNetworkApi>()

    override suspend fun searchFood(query: String, page: Int, pageSize: Int): List<NetworkProduct> =
        networkApi.searchFood(query, page, pageSize).products
}
