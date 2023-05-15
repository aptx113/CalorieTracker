package com.dante.calorietracker.feature.age

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dante.calorietracker.core.domain.FilterOutDigitsUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.testing.data.repository.emptyUserInfo
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue


class AgeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = spyk(TestUserDataRepository())

    private val viewModel = AgeViewModel(repository, FilterOutDigitsUseCase(), SavedStateHandle())

    @Test
    fun onAgeEnter_enterAge_returnFromSavedStateHandle() = runTest {
        viewModel.onAgeEntered("1")
        viewModel.age.test {
            assertEquals("1", awaitItem())
        }
    }

    @Test
    fun onAgeEnter_enterInvalidAge_returnEmptyString() = runTest {
        viewModel.onAgeEntered("1111")
        viewModel.age.test {
            assertNotEquals("1111", awaitItem())
        }
    }

    @Test
    fun onNextClick_enterValidAge_ageIsSavedAndShouldDisplayAgeNotFilledIsFalse() = runTest {
        viewModel.onAgeEntered("1")
        viewModel.onNextClick {}
        repository.userInfo.test {
            assertEquals(emptyUserInfo.copy(age = 1), awaitItem())
            assertFalse(viewModel.shouldDisplayAgeNotFilled)
        }
    }

    @Test
    fun onNextClick_emptyAge_ageIsNotSavedAndShouldDisplayAgeNotFilledIsTrue() = runTest {
        viewModel.onAgeEntered("")
        viewModel.onNextClick {}
        assertTrue(viewModel.shouldDisplayAgeNotFilled)
        coVerify(inverse = true) { repository.saveAge(1) }
    }
}
