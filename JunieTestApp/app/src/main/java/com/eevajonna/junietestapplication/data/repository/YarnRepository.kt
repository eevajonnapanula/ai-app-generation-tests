package com.eevajonna.junietestapplication.data.repository

import com.eevajonna.junietestapplication.data.dao.YarnDao
import com.eevajonna.junietestapplication.data.model.Yarn
import kotlinx.coroutines.flow.Flow

/**
 * Repository for accessing Yarn data.
 */
class YarnRepository(private val yarnDao: YarnDao) {
    
    /**
     * Get all yarns from the database.
     * @return A flow of all yarns.
     */
    fun getAllYarns(): Flow<List<Yarn>> = yarnDao.getAllYarns()
    
    /**
     * Get a yarn by its ID.
     * @param id The ID of the yarn.
     * @return The yarn with the given ID.
     */
    fun getYarnById(id: Long): Flow<Yarn> = yarnDao.getYarnById(id)
    
    /**
     * Insert a new yarn into the database.
     * @param yarn The yarn to insert.
     * @return The ID of the inserted yarn.
     */
    suspend fun insertYarn(yarn: Yarn): Long = yarnDao.insert(yarn)
    
    /**
     * Update an existing yarn in the database.
     * @param yarn The yarn to update.
     */
    suspend fun updateYarn(yarn: Yarn) = yarnDao.update(yarn)
    
    /**
     * Delete a yarn from the database.
     * @param yarn The yarn to delete.
     */
    suspend fun deleteYarn(yarn: Yarn) = yarnDao.delete(yarn)
}