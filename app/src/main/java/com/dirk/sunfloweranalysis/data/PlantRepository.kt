package com.dirk.sunfloweranalysis.data

import javax.inject.Inject
import javax.inject.Singleton


/**
 * 在主线程call， Room 是支持Coroutine的， 避免Db IO 在主线程执行
 *
 *      如果Dao 是基于数据，提供CRUD操作，具体实现肯定是通过子线程的方式；而 Repository则是对Dao 进行按需组装，它是具有业务特性的。
 */
@Singleton
class PlantRepository @Inject constructor(private val plantDao: PlantDao){

    fun getPlants() = plantDao.getPants()

    fun getPant(plantId : String) = plantDao.getPant(plantId)

    fun getPlantsWithGrowZoneNumber(growZoneNumber: Int) =
        plantDao.getPlantsWithGrowZoneNumber(growZoneNumber)
}