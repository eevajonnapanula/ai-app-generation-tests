package com.eevajonna.junietestapplication.data.model

/**
 * Enum representing the different types of knitting needles.
 */
enum class NeedleType {
    SINGLE_POINTED,
    DOUBLE_POINTED,
    CIRCULAR;
    
    companion object {
        /**
         * Returns a user-friendly display name for the needle type.
         */
        fun getDisplayName(type: NeedleType): String {
            return when (type) {
                SINGLE_POINTED -> "Single Pointed"
                DOUBLE_POINTED -> "Double Pointed"
                CIRCULAR -> "Circular"
            }
        }
        
        /**
         * Returns a list of all needle types as display names.
         */
        fun getDisplayNames(): List<String> {
            return values().map { getDisplayName(it) }
        }
        
        /**
         * Converts a display name to the corresponding NeedleType.
         */
        fun fromDisplayName(displayName: String): NeedleType {
            return when (displayName) {
                "Single Pointed" -> SINGLE_POINTED
                "Double Pointed" -> DOUBLE_POINTED
                "Circular" -> CIRCULAR
                else -> throw IllegalArgumentException("Unknown needle type: $displayName")
            }
        }
    }
}