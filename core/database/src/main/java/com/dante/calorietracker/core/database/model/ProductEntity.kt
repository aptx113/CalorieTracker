package com.dante.calorietracker.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "product")
data class ProductEntity(
    @ColumnInfo("image_front_thumb_url")
    val imageFrontThumbUrl: String?,
    val nutriments: NutrimentsEntity,
    @ColumnInfo("product_name")
    val productName: String?
)
