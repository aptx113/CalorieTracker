package com.dante.calorietracker.feature.gender

import app.cash.turbine.test
import com.dante.calorietracker.core.model.Gender
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.testing.data.repository.emptyUserInfo
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class GenderViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userDataRepository = TestUserDataRepository()

    private lateinit var viewModel: GenderViewModel

    @Before
    fun setup() {
        viewModel = GenderViewModel(userDataRepository)
    }

    @Test
    fun onGenderClick_clickMale_genderSelectionUpdates() = runTest {
        viewModel.onGenderClick(Gender.Male)
        assertEquals(Gender.Male, viewModel.selectedGender)
    }

    @Test
    fun onNextClick_selectMale_updatesUsersGender() = runTest {
        viewModel.onGenderClick(Gender.Male)
        viewModel.onNextClick()
        userDataRepository.userInfo.test {
            assertEquals(emptyUserInfo.copy(gender = Gender.Male), awaitItem())
        }
    }
}
