package com.eevajonna.claudetestapp.data.model

data class Yarn(
    val id: String,
    val brandName: String,
    val yarnName: String,
    val colorway: String,
    val yardageMeters: Int,
    val weightGrams: Int,
    val skeinCount: Int,
    val pictureUri: String? = null
)