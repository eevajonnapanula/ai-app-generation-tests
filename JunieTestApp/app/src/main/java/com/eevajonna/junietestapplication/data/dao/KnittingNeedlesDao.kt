package com.eevajonna.junietestapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.eevajonna.junietestapplication.data.model.KnittingNeedles
import com.eevajonna.junietestapplication.data.model.NeedleType
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the KnittingNeedles entity.
 */
@Dao
interface KnittingNeedlesDao {
    /**
     * Insert new knitting needles into the database.
     * @param knittingNeedles The knitting needles to insert.
     * @return The ID of the inserted knitting needles.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(knittingNeedles: KnittingNeedles): Long
    
    /**
     * Update existing knitting needles in the database.
     * @param knittingNeedles The knitting needles to update.
     */
    @Update
    suspend fun update(knittingNeedles: KnittingNeedles)
    
    /**
     * Delete knitting needles from the database.
     * @param knittingNeedles The knitting needles to delete.
     */
    @Delete
    suspend fun delete(knittingNeedles: KnittingNeedles)
    
    /**
     * Get knitting needles by their ID.
     * @param id The ID of the knitting needles.
     * @return The knitting needles with the given ID.
     */
    @Query("SELECT * FROM knitting_needles WHERE id = :id")
    fun getKnittingNeedlesById(id: Long): Flow<KnittingNeedles>
    
    /**
     * Get all knitting needles from the database.
     * @return A flow of all knitting needles.
     */
    @Query("SELECT * FROM knitting_needles ORDER BY sizeInMm, type")
    fun getAllKnittingNeedles(): Flow<List<KnittingNeedles>>
    
    /**
     * Get all knitting needles of a specific type.
     * @param type The type of knitting needles to get.
     * @return A flow of all knitting needles of the given type.
     */
    @Query("SELECT * FROM knitting_needles WHERE type = :type ORDER BY sizeInMm")
    fun getKnittingNeedlesByType(type: NeedleType): Flow<List<KnittingNeedles>>
}