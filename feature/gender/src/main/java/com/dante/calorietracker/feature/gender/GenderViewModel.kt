package com.dante.calorietracker.feature.gender

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dante.calorietracker.core.data.repository.UserDataRepository
import com.dante.calorietracker.core.model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(private val repository: UserDataRepository) :
    ViewModel() {

    var selectedGender by mutableStateOf<Gender>(Gender.Female)
        private set

    fun onGenderClick(gender: Gender) {
        selectedGender = gender
    }

    fun onNextClick() = viewModelScope.launch {
        repository.saveGender(selectedGender)
    }
}
