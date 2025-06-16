package com.example.cursortestapp.model

data class Yarn(
    val id: Long = 0,
    val brandName: String,
    val yarnName: String,
    val colorway: String,
    val yardageMeters: Int,
    val weightGrams: Double,
    val skeinCount: Int,
    val imageUri: String? = null
) 