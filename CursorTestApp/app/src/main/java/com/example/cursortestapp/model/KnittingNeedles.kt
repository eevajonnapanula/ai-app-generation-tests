package com.example.cursortestapp.model

enum class NeedleType {
    SINGLE_POINTED,
    DOUBLE_POINTED,
    CIRCULAR
}

data class KnittingNeedles(
    val id: Long = 0,
    val brandName: String,
    val sizeMm: Double,
    val type: NeedleType
) 