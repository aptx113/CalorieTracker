package com.dante.calorietracker.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(val products: List<NetworkProduct>)
