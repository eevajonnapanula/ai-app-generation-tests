package com.eevajonna.geminitestapp.data

import androidx.compose.ui.graphics.ImageBitmap

data class Yarn(
    val id: Int,
    val brand: String,
    val name: String,
    val colorway: String,
    val meterage: Int,
    val skeinWeight: Int,
    val skeins: Int,
    val picture: ImageBitmap? = null // Can be null initially
)