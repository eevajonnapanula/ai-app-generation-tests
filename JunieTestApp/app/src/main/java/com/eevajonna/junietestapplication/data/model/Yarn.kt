package com.eevajonna.junietestapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a yarn in the user's stash.
 */
@Entity(tableName = "yarns")
data class Yarn(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /**
     * Brand name of the yarn (e.g., "Lion Brand", "Malabrigo")
     */
    val brandName: String,
    
    /**
     * Name of the specific yarn (e.g., "Wool-Ease", "Rios")
     */
    val yarnName: String,
    
    /**
     * Color name or number of the yarn (e.g., "Claret", "412")
     */
    val colorway: String,
    
    /**
     * Yardage in meters per skein
     */
    val yardageInMeters: Int,
    
    /**
     * Weight of a single skein in grams
     */
    val weightOfSkein: Int,
    
    /**
     * Number of skeins in the stash
     */
    val amountOfSkeins: Int,
    
    /**
     * URI to the picture of the yarn
     */
    val pictureUri: String? = null
)