package com.eevajonna.junietestapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eevajonna.junietestapplication.data.dao.KnittingNeedlesDao
import com.eevajonna.junietestapplication.data.dao.YarnDao
import com.eevajonna.junietestapplication.data.model.KnittingNeedles
import com.eevajonna.junietestapplication.data.model.NeedleTypeConverter
import com.eevajonna.junietestapplication.data.model.Yarn

/**
 * The Room database for this app.
 */
@Database(
    entities = [Yarn::class, KnittingNeedles::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NeedleTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    
    /**
     * Gets the DAO for Yarn entities.
     */
    abstract fun yarnDao(): YarnDao
    
    /**
     * Gets the DAO for KnittingNeedles entities.
     */
    abstract fun knittingNeedlesDao(): KnittingNeedlesDao
    
    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        /**
         * Gets the singleton instance of the database.
         */
        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yarn_stash_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}