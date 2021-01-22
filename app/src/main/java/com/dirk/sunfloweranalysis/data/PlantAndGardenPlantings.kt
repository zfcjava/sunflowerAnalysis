package com.dirk.sunfloweranalysis.data

import androidx.room.Embedded
import androidx.room.Relation


/**
 * 这相当于一个基于关系的 视图
 *
 * 如果不是data类型， Cannot find setter for field
 */
data class PlantAndGardenPlantings(
    @Embedded
    val plant: Plant,

    //TODO 我们在写代码时也应该注意， java or kotlin 是驼峰，db则是下划线链接，这样就便于区分了
    @Relation(parentColumn = "id",entityColumn = "plant_id")
    val gardenPlantings: List<GardenPlanting> = emptyList()
)