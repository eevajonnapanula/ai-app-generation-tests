package com.eevajonna.junietestapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.eevajonna.junietestapplication.data.model.Yarn
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the Yarn entity.
 */
@Dao
interface YarnDao {
    /**
     * Insert a new yarn into the database.
     * @param yarn The yarn to insert.
     * @return The ID of the inserted yarn.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(yarn: Yarn): Long
    
    /**
     * Update an existing yarn in the database.
     * @param yarn The yarn to update.
     */
    @Update
    suspend fun update(yarn: Yarn)
    
    /**
     * Delete a yarn from the database.
     * @param yarn The yarn to delete.
     */
    @Delete
    suspend fun delete(yarn: Yarn)
    
    /**
     * Get a yarn by its ID.
     * @param id The ID of the yarn.
     * @return The yarn with the given ID.
     */
    @Query("SELECT * FROM yarns WHERE id = :id")
    fun getYarnById(id: Long): Flow<Yarn>
    
    /**
     * Get all yarns from the database.
     * @return A flow of all yarns.
     */
    @Query("SELECT * FROM yarns ORDER BY brandName, yarnName")
    fun getAllYarns(): Flow<List<Yarn>>
}