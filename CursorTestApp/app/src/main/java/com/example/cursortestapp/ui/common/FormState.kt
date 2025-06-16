package com.example.cursortestapp.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FormState<T>(
    initialValue: T,
    private val validator: (T) -> Boolean = { true }
) {
    var value by mutableStateOf(initialValue)
        private set
    
    var error by mutableStateOf<String?>(null)
        private set

    fun update(newValue: T) {
        value = newValue
        validate()
    }

    fun validate(): Boolean {
        error = if (validator(value)) null else "Invalid value"
        return error == null
    }

    fun hasError(): Boolean = error != null
} 