package com.dirk.sunfloweranalysis.viewmodels

import androidx.lifecycle.*
import com.dirk.sunfloweranalysis.BuildConfig
import com.dirk.sunfloweranalysis.data.GardenPlantingRepository
import com.dirk.sunfloweranalysis.data.Plant
import com.dirk.sunfloweranalysis.data.PlantRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch


/**
 * 这里声明的属性，根据MVVM架构，应该由Model来提供，供当前ViewModel
 *
 * TODO 思考这里的 plantRepository 为什么不使用 @Inject进行注入
 */
class PlantDetailViewModel @AssistedInject constructor(
    plantRepository: PlantRepository,
    private val gardenPlantingRepository: GardenPlantingRepository,
    @Assisted private val plantId: String
    ) :ViewModel(){

    /**
     * 这里的属性，根据MVVM架构，应该提供给 View去使用
     */
    val isPlanted = gardenPlantingRepository.isPlanted(plantId).asLiveData()

    /**
     * 这里的属性，根据MVVM架构，应该提供给 View去使用
     */
    val plant = plantRepository.getPant(plantId).asLiveData()


    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")


    /**
     * 加入到db中
     */
    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }

    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(plantId: String): PlantDetailViewModel
    }

    companion object {

        /**
         * 这个工厂功能  利用AssistedFactory 将plantId 作为入参，生成一个具体的Model
         */
        fun provideFactory(factory:AssistedFactory, plantId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(plantId) as T
            }
        }
    }
}