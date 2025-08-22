package com.eevajonna.claudetestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eevajonna.claudetestapp.data.model.KnittingNeedle
import com.eevajonna.claudetestapp.data.model.Yarn
import com.eevajonna.claudetestapp.repository.YarnRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class YarnStashViewModel(private val repository: YarnRepository) : ViewModel() {
    val yarns: StateFlow<List<Yarn>> = repository.yarns
    val needles: StateFlow<List<KnittingNeedle>> = repository.needles

    fun addYarn(yarn: Yarn) {
        viewModelScope.launch {
            repository.addYarn(yarn)
        }
    }

    fun addNeedle(needle: KnittingNeedle) {
        viewModelScope.launch {
            repository.addNeedle(needle)
        }
    }

    fun getYarnById(id: String): Yarn? = repository.getYarnById(id)

    fun getNeedleById(id: String): KnittingNeedle? = repository.getNeedleById(id)
}