package com.dante.calorietracker.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkProduct(
    @SerialName("image_front_thumb_url")
    val imageFrontThumbUrl: String?,
    val nutriments: NetworkNutriments,
    @SerialName("product_name")
    val productName: String?
)
