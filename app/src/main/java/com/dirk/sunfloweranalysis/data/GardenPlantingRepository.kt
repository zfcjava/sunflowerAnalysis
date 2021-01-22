package com.dirk.sunfloweranalysis.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GardenPlantingRepository @Inject constructor(private val gardenPlantingDao: GardenPlantingDao) {

    /**
     * 添加一个植物
     */
    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = GardenPlanting(plantId)
        gardenPlantingDao.insertGardenPlanting(gardenPlanting)
    }

    suspend fun removeGardenPlanting(gardenPlanting: GardenPlanting){
        gardenPlantingDao.deleteGardenPlanting(gardenPlanting)
    }

    fun  isPlanted(plantId: String):Flow<Boolean> = gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

}