package com.eevajonna.junietestapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

/**
 * Type converter for NeedleType enum to store in Room database
 */
class NeedleTypeConverter {
    @TypeConverter
    fun fromNeedleType(needleType: NeedleType): String {
        return needleType.name
    }
    
    @TypeConverter
    fun toNeedleType(value: String): NeedleType {
        return NeedleType.valueOf(value)
    }
}

/**
 * Data class representing knitting needles in the user's collection.
 */
@Entity(tableName = "knitting_needles")
@TypeConverters(NeedleTypeConverter::class)
data class KnittingNeedles(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * Brand name of the knitting needles (e.g., "ChiaoGoo", "Addi")
     */
    val brandName: String,
    
    /**
     * Size of the knitting needles in millimeters
     */
    val sizeInMm: Float,
    
    /**
     * Type of the knitting needles (single pointed, double pointed, circular)
     */
    val type: NeedleType
)