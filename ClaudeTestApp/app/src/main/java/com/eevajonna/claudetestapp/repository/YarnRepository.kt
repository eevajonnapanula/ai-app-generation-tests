package com.eevajonna.claudetestapp.repository

import com.eevajonna.claudetestapp.data.model.KnittingNeedle
import com.eevajonna.claudetestapp.data.model.Yarn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class YarnRepository {
    private val _yarns = MutableStateFlow<List<Yarn>>(emptyList())
    val yarns: StateFlow<List<Yarn>> = _yarns.asStateFlow()

    private val _needles = MutableStateFlow<List<KnittingNeedle>>(emptyList())
    val needles: StateFlow<List<KnittingNeedle>> = _needles.asStateFlow()

    fun addYarn(yarn: Yarn) {
        _yarns.value = _yarns.value + yarn
    }

    fun addNeedle(needle: KnittingNeedle) {
        _needles.value = _needles.value + needle
    }

    fun getYarnById(id: String): Yarn? = _yarns.value.find { it.id == id }

    fun getNeedleById(id: String): KnittingNeedle? = _needles.value.find { it.id == id }
}