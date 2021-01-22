package com.dirk.sunfloweranalysis.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dirk.sunfloweranalysis.data.AppDatabase
import com.dirk.sunfloweranalysis.data.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.samples.apps.sunflower.utilities.PLANT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope
import kotlin.Exception

class SeedDatabaseWorker(appContext: Context,
                         params: WorkerParameters)
                        : CoroutineWorker(appContext,params){

    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PLANT_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val plantType = object :TypeToken<List<Plant>>(){}.type

                    val plantList:List<Plant> = Gson().fromJson(jsonReader,plantType)
                    //TODO 到了底层MODEL中，就不适用model依赖注入了。  局部变量
                    val database = AppDatabase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)
                    Result.success()
                }
            }
        } catch (ex :Exception){
            Result.failure()
        }
    }
}

