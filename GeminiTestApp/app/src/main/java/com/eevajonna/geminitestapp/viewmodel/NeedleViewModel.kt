package com.eevajonna.geminitestapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.eevajonna.geminitestapp.data.Needle
import com.eevajonna.geminitestapp.data.NeedleType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NeedleViewModel : ViewModel() {
    private val _needles = MutableStateFlow<List<Needle>>(emptyList())
    val needles: StateFlow<List<Needle>> = _needles.asStateFlow()

    var selectedNeedle: Needle? by mutableStateOf(null)
        private set

    fun selectNeedle(needle: Needle) {
        selectedNeedle = needle
    }

    fun addNeedle(
        brand: String,
        size: Double,
        type: NeedleType
    ) {
        val newNeedle = Needle(
            id = _needles.value.size + 1,
            brand = brand,
            size = size,
            type = type
        )
        _needles.update { it + newNeedle }
    }
}