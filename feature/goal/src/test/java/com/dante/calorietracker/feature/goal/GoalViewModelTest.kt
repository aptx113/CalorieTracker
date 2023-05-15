package com.dante.calorietracker.feature.goal

import app.cash.turbine.test
import com.dante.calorietracker.core.model.GoalType
import com.dante.calorietracker.core.testing.data.repository.TestUserDataRepository
import com.dante.calorietracker.core.testing.data.repository.emptyUserInfo
import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class GoalViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = TestUserDataRepository()
    private val viewModel = GoalViewModel(repository)

    @Test
    fun selectedGoalType_defaultIsKeep() {
        assertEquals(viewModel.selectedGoal, GoalType.KeepWeight)
    }

    @Test
    fun onGoalSelect_selectLose_selectedGoalIsLose() {
        viewModel.onGoalSelect(GoalType.LoseWeight)
        assertEquals(viewModel.selectedGoal, GoalType.LoseWeight)
    }

    @Test
    fun onNextClick_selectGain_userInfoReturnsGoalGain() = runTest {
        viewModel.onGoalSelect(GoalType.GainWeight)
        viewModel.onNextClick { }
        repository.userInfo.test {
            assertEquals(emptyUserInfo.copy(goalType = GoalType.GainWeight), awaitItem())
        }
    }
}
