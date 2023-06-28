package com.dante.calorietracker.core

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>
    data class Error(val exception: Throwable? = null) : ApiResult<Nothing>
    object Loading : ApiResult<Nothing>
}

fun <T> Flow<T>.asApiResult(): Flow<ApiResult<T>> {
    return this
        .map<T, ApiResult<T>> {
            ApiResult.Success(it)
        }
        .onStart { emit(ApiResult.Loading) }
        .catch {
            Log.e("ApiResult", "Error: ${it.message}")
            emit(ApiResult.Error(it))
        }
}
