package com.dante.calorietracker.core.domain

import org.junit.Test

class DecimalValidatorUseCaseTest {

    private val useCase = DecimalValidatorUseCase()

    @Test
    fun inputStartWithZero_numberCountGreaterThanOne_returnValueNotStartWithZero(){
        val result = useCase("012",3,2)
        assert(result == "12")
    }
}
