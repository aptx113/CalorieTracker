package com.dante.calorietracker.core.ui.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorStringFor: (String) -> String = { "" },
    private val errorResFor: (String) -> Int = { 0 }
) {
    var text by mutableStateOf("")

    var isFocusedDirty by mutableStateOf(false)
    var isFocused by mutableStateOf(false)
    var displayErrors by mutableStateOf(false)

    open val isValid: Boolean
        get() = validator(text)

    fun onFocusChange(focused: Boolean) {
        isFocused = focused
        if (focused) isFocusedDirty = true
    }

    fun validateState(action: () -> Unit) {
        if (isValid) {
            displayErrors = false
            action()
        } else {
            enableShowError()
        }
    }

    fun enableShowError() {
        if (isFocusedDirty) {
            displayErrors = true
        }
    }

    fun showErrors() = !isValid && displayErrors

    open fun getErrorString(): String? {
        return if (showErrors()) errorStringFor(text) else null
    }

    open fun getErrorRes(): Int? {
        return if (showErrors()) errorResFor(text) else null
    }
}

fun textFieldStateSaver(state: TextFieldState) = listSaver<TextFieldState, Any>(
    save = { listOf(it.text, it.isFocusedDirty) },
    restore = {
        state.apply {
            text = it[0] as String
            isFocusedDirty = it[1] as Boolean
        }
    }
)