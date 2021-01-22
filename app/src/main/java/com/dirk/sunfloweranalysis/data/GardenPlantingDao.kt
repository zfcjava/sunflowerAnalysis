package com.dirk.sunfloweranalysis.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


/**
 *
 *   代码中的表名字 比如"garden_plantings" 它的声明在 数据库Entity对象的注解上
 *
 *
 */

@Dao
interface GardenPlantingDao {


    @Query("SELECT * FROM garden_plantings")
    fun getGardenPlantings(): Flow<List<GardenPlanting>>

    @Query("SELECT EXISTS(SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: String): Flow<Boolean>

    /**
     * This query will tell Room to query both the [Plant] and [GardenPlanting] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    fun getPlantedGardens(): Flow<List<PlantAndGardenPlantings>>

    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long

    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)
}