package com.eevajonna.geminitestapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.eevajonna.geminitestapp.data.Yarn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class YarnViewModel : ViewModel() {
    private val _yarns = MutableStateFlow<List<Yarn>>(emptyList())
    val yarns: StateFlow<List<Yarn>> = _yarns.asStateFlow()

    var selectedYarn: Yarn? by mutableStateOf(null)
        private set

    fun selectYarn(yarn: Yarn) {
        selectedYarn = yarn
    }

    fun addYarn(
        brand: String,
        name: String,
        colorway: String,
        meterage: Int,
        skeinWeight: Int,
        skeins: Int,
        picture: ImageBitmap?
    ) {
        val newYarn = Yarn(
            id = _yarns.value.size + 1,
            brand = brand,
            name = name,
            colorway = colorway,
            meterage = meterage,
            skeinWeight = skeinWeight,
            skeins = skeins,
            picture = picture
        )
        _yarns.update { it + newYarn }
    }
}