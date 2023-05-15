package com.dante.calorietracker.core.domain

import org.junit.Test

class DecimalValidatorUseCaseTest {

    private val useCase = DecimalValidatorUseCase()

    @Test
    fun inputStartWithZero_numberCountGreaterThanOne_returnValueNotStartWithZero() {
        val result = useCase("012", 3, 2)
        assert(result == "12")
    }

    @Test
    fun inputLengthGreaterThanNumberCount_decimalCountZero_returnLengthEqualsToNumberCount() {
        val result = useCase("12345678", 4, 0)
        assert(result.length == 4)
    }
}
