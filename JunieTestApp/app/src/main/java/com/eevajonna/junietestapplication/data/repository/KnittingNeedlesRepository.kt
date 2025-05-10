package com.eevajonna.junietestapplication.data.repository

import com.eevajonna.junietestapplication.data.dao.KnittingNeedlesDao
import com.eevajonna.junietestapplication.data.model.KnittingNeedles
import com.eevajonna.junietestapplication.data.model.NeedleType
import kotlinx.coroutines.flow.Flow

/**
 * Repository for accessing KnittingNeedles data.
 */
class KnittingNeedlesRepository(private val knittingNeedlesDao: KnittingNeedlesDao) {
    
    /**
     * Get all knitting needles from the database.
     * @return A flow of all knitting needles.
     */
    fun getAllKnittingNeedles(): Flow<List<KnittingNeedles>> = knittingNeedlesDao.getAllKnittingNeedles()
    
    /**
     * Get knitting needles by their ID.
     * @param id The ID of the knitting needles.
     * @return The knitting needles with the given ID.
     */
    fun getKnittingNeedlesById(id: Long): Flow<KnittingNeedles> = knittingNeedlesDao.getKnittingNeedlesById(id)
    
    /**
     * Get all knitting needles of a specific type.
     * @param type The type of knitting needles to get.
     * @return A flow of all knitting needles of the given type.
     */
    fun getKnittingNeedlesByType(type: NeedleType): Flow<List<KnittingNeedles>> = 
        knittingNeedlesDao.getKnittingNeedlesByType(type)
    
    /**
     * Insert new knitting needles into the database.
     * @param knittingNeedles The knitting needles to insert.
     * @return The ID of the inserted knitting needles.
     */
    suspend fun insertKnittingNeedles(knittingNeedles: KnittingNeedles): Long = 
        knittingNeedlesDao.insert(knittingNeedles)
    
    /**
     * Update existing knitting needles in the database.
     * @param knittingNeedles The knitting needles to update.
     */
    suspend fun updateKnittingNeedles(knittingNeedles: KnittingNeedles) = 
        knittingNeedlesDao.update(knittingNeedles)
    
    /**
     * Delete knitting needles from the database.
     * @param knittingNeedles The knitting needles to delete.
     */
    suspend fun deleteKnittingNeedles(knittingNeedles: KnittingNeedles) = 
        knittingNeedlesDao.delete(knittingNeedles)
}