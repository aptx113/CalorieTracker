package com.dante.calorietracker.core.network

import com.dante.calorietracker.core.network.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CalorieTrackerNetworkApi {
    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,nutriments,image_front_thumb_url")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int
    ): SearchResult
}
