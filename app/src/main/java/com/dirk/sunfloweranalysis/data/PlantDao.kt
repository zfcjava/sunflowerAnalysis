package com.dirk.sunfloweranalysis.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * Dao 核心功能执行 是 在协程中执行的
 */
@Dao
interface PlantDao {

    @Query("SELECT * FROM plants ORDER BY name")
    fun getPants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPant(plantId: String): Flow<Plant>

    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): Flow<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants:List<Plant>)
}