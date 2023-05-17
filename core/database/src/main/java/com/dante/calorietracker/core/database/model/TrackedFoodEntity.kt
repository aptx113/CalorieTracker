package com.dante.calorietracker.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracked_food")
data class TrackedFoodEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val carbs: Int,
    val proteins: Int,
    val fats: Int,
    val imageUrl: String?,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    val calories: Int
)
