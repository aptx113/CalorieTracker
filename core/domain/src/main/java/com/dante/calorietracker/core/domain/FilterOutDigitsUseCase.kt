package com.dante.calorietracker.core.domain

import javax.inject.Inject

class FilterOutDigitsUseCase @Inject constructor() {
    operator fun invoke(input: String): String {
        return input.filter { it.isDigit() }
    }
}
