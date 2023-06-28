package com.dante.calorietracker.core.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> List<T>.mapOrReplace(targetItem: T, newItem: T) = map {
    if (it == targetItem) {
        newItem
    } else {
        it
    }
}

fun <T> Flow<List<T>>.mapOrReplace(targetItem: T, newItem: T) = map {
    it.mapOrReplace(targetItem, newItem)
}
