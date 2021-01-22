package com.dirk.sunfloweranalysis.di

import android.content.Context
import com.dirk.sunfloweranalysis.data.AppDatabase
import com.dirk.sunfloweranalysis.data.GardenPlantingDao
import com.dirk.sunfloweranalysis.data.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    //TODO 这里应该是说的是appDatabase是单例，但是为什么注解不放在AppDatabase上呢？ 抽象类
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase):PlantDao{
        return appDatabase.plantDao()
    }

    @Provides
    fun provideGardenPlantingDao(appDatabase: AppDatabase):GardenPlantingDao{
        return appDatabase.gardenPlantingDao()
    }


}