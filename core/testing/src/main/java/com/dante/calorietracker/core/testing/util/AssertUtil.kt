package com.dante.calorietracker.core.testing.util

import io.mockk.MockKVerificationScope
import io.mockk.coVerify
import io.mockk.coVerifyAll

fun coVerifyNot(verifyBlock: suspend MockKVerificationScope.() -> Unit) =
    coVerify(inverse = true, verifyBlock = verifyBlock)
