package com.dante.calorietracker.feature.weight

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.testing.data.repository.emptyUserInfo
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class WeightViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = spyk(TestUserDataRepository())
    private val viewModel = WeightViewModel(
        repository, DecimalValidatorUseCase(),
        SavedStateHandle()
    )

    @Test
    fun onWeightEnter_enterWeight_returnFromWeightSavedStateHandle() = runTest {
        viewModel.onWeightEnter("66.2")
        viewModel.weight.test {
            assertEquals("66.2", awaitItem())
        }
    }

    @Test
    fun onWeightEnter_enterInvalidWeight_returnWeightLengthEqualsNumberCount() = runTest {
        viewModel.onWeightEnter("1111")
        val weight = viewModel.weight.first()

        assertNotEquals("1111", weight)
        assertEquals("111", weight)
    }

    @Test
    fun onNextClick_enterValidAWeight_weightIsSavedAndShouldDisplayWeightNotFilledIsFalse() =
        runTest {
            viewModel.onWeightEnter("66.7")
            viewModel.onNextClick {}
            repository.userInfo.test {
                assertEquals(emptyUserInfo.copy(weight = 66.7f), awaitItem())
                assertFalse(viewModel.shouldDisplayWeightNotFilled)
            }
        }

    @Test
    fun onNextClick_emptyWeight_weightIsNotSavedAndShouldDisplayWeightNotFilledIsTrue() = runTest {
        viewModel.onWeightEnter("")
        viewModel.onNextClick {}
        assertTrue(viewModel.shouldDisplayWeightNotFilled)
        coVerify(inverse = true) { repository.saveWeight(1f) }
    }
}
