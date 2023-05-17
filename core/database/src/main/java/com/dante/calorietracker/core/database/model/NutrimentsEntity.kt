package com.dante.calorietracker.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "nutriments")
data class NutrimentsEntity(
    @ColumnInfo("carbohydrates_100g")
    val carbohydrates100g: Double,
    @ColumnInfo("energy-kcal_100g")
    val energyKcal100g: Double,
    @ColumnInfo("fat_100g")
    val fat100g: Double,
    @ColumnInfo("proteins_100g")
    val proteins100g: Double
)
