package com.dante.calorietracker.core.domain

import java.text.DecimalFormat
import java.util.Locale
import javax.inject.Inject

class DecimalValidatorUseCase @Inject constructor() {
    operator fun invoke(input: String, numberCount: Int, decimalCount: Int): String {
        val decimalFormat = DecimalFormat.getInstance(Locale.getDefault()) as DecimalFormat
        val decimalSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator
        val filteredChars = input.filterIndexed { index, char ->
            char.isDigit() ||
                    (char == decimalSeparator && index != 0 && input.indexOf(decimalSeparator) == index) ||
                    (char == decimalSeparator && index != 0 && input.count { it == decimalSeparator } <= 1)

        }
        val beforeDecimal = filteredChars.substringBefore(decimalSeparator).take(numberCount)
        val afterDecimal = filteredChars.substringAfter(decimalSeparator).take(decimalCount)


        return when {
            filteredChars.count { it == decimalSeparator } == 1 && beforeDecimal.length > 1 && beforeDecimal.startsWith(
                "0"
            ) -> {
                beforeDecimal.drop(1) + decimalSeparator + afterDecimal
            }

            filteredChars.count { it == decimalSeparator } == 1 -> {
                beforeDecimal + decimalSeparator + afterDecimal
            }

            !filteredChars.contains(decimalSeparator) && beforeDecimal.length > 1 && beforeDecimal.startsWith(
                "0"
            ) -> {
                beforeDecimal.drop(1)
            }

            else -> {
                filteredChars.take(numberCount)
            }
        }
    }
}
