package com.eevajonna.geminitestapp.data

data class Needle(
    val id: Int,
    val brand: String,
    val size: Double,
    val type: NeedleType
)

enum class NeedleType {
    SinglePointed, DoublePointed, Circular
}