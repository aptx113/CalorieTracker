package com.dante.calorietracker.feature.nutrientgoal

import app.cash.turbine.test
import com.dante.calorietracker.core.domain.DecimalValidatorUseCase
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.testing.data.repository.emptyUserInfo
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import com.dante.calorietracker.core.testing.util.coVerifyNot
import com.dante.calorietracker.core.ui.state.TextFieldState
import com.dante.calorietracker.feature.nutrientGoal.Nutrient
import com.dante.calorietracker.feature.nutrientGoal.NutrientGoalState
import com.dante.calorietracker.feature.nutrientGoal.NutrientGoalViewModel
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class NutrientGoalViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = spyk(TestUserDataRepository())

    private val viewModel = NutrientGoalViewModel(repository, DecimalValidatorUseCase())

    private lateinit var carbsState: TextFieldState
    private lateinit var proteinsState: TextFieldState
    private lateinit var fatsState: TextFieldState

    @Before
    fun setup() {
        carbsState = NutrientGoalState(Nutrient.Carbs)
        proteinsState = NutrientGoalState(Nutrient.Protein)
        fatsState = NutrientGoalState(Nutrient.Fat)
    }

    @Test
    fun onNextClick_allNutrientsAreValid_nutrientsAreSaved() = runTest {
        carbsState.text = "20"
        proteinsState.text = "30"
        fatsState.text = "10"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        repository.userInfo.test {
            assertEquals(
                emptyUserInfo.copy(carbRatio = 20f, proteinRatio = 30f, fatRatio = 10f),
                awaitItem()
            )
        }
    }

    @Test
    fun onNextClick_carbsIsInValid_nutrientsNotSaved() = runTest {
        proteinsState.text = "30"
        fatsState.text = "10"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }

    @Test
    fun onNextClick_proteinsIsInValid_nutrientsNotSaved() = runTest {
        carbsState.text = "10"
        fatsState.text = "10"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }

    @Test
    fun onNextClick_fatsIsInValid_nutrientsNotSaved() = runTest {
        carbsState.text = "10"
        proteinsState.text = "10"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }

    @Test
    fun onNextClick_carbsAndProteinsIsInValid_nutrientsNotSaved() = runTest {
        fatsState.text = "10"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }

    @Test
    fun onNextClick_carbsAndFatsIsInValid_nutrientsNotSaved() = runTest {
        proteinsState.text = "20"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }

    @Test
    fun onNextClick_proteinsAndFatsIsInValid_nutrientsNotSaved() = runTest {
        carbsState.text = "10"

        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }

    @Test
    fun onNextClick_allNutrientsInValid_nutrientsNotSaved() = runTest {
        viewModel.onNextClick(carbsState, proteinsState, fatsState) {}
        coVerifyNot {
            repository.saveCarbRatio(any())
            repository.saveProteinRatio(any())
            repository.saveFatRatio(any())
        }
    }
}
