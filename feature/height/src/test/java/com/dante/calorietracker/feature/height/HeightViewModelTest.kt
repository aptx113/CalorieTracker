package com.dante.calorietracker.feature.height

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dante.calorietracker.core.domain.FilterOutDigitsUseCase
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


class HeightViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = spyk(TestUserDataRepository())
    private val viewModel = HeightViewModel(
        repository,
        FilterOutDigitsUseCase(),
        SavedStateHandle()
    )

    @Test
    fun onHeightEnter_enterHeight_returnFromHeightSavedStateHandle() = runTest {
        viewModel.onHeightEnter("169")
        viewModel.height.test {
            assertEquals("169", awaitItem())
        }
    }

    @Test
    fun onHeightEnter_enterInvalidHeight_returnEmptyString() = runTest {
        viewModel.onHeightEnter("1111")
        val height = viewModel.height.first()

        assertNotEquals("1111", height)
        assertEquals("", height)
    }

    @Test
    fun onNextClick_enterValidAHeight_heightIsSavedAndShouldDisplayHeightNotFilledIsFalse() =
        runTest {
            viewModel.onHeightEnter("169")
            viewModel.onNextClick {}
            repository.userInfo.test {
                assertEquals(emptyUserInfo.copy(height = 169), awaitItem())
                assertFalse(viewModel.shouldDisplayHeightNotFilled)
            }
        }

    @Test
    fun onNextClick_emptyHeight_heightIsNotSavedAndShouldDisplayHeightNotFilledIsTrue() = runTest {
        viewModel.onHeightEnter("")
        viewModel.onNextClick {}
        assertTrue(viewModel.shouldDisplayHeightNotFilled)
        coVerify(inverse = true) { repository.saveHeight(1) }
    }
}
