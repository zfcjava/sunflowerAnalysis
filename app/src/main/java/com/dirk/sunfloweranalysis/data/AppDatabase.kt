package com.dirk.sunfloweranalysis.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dirk.sunfloweranalysis.workers.SeedDatabaseWorker
import com.google.samples.apps.sunflower.utilities.DATABASE_NAME


/**
 * TODO
 *  1.exportSchema 是什么意思
 *  2.db 如何升级
 */
@Database(entities = [GardenPlanting::class , Plant::class], version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase  :RoomDatabase(){
    abstract fun gardenPlantingDao():GardenPlantingDao

    abstract fun plantDao():PlantDao


    companion object{
        @Volatile private var instance : AppDatabase? = null

        fun getInstance( context: Context): AppDatabase{
            return instance?: synchronized(this){
                instance?: buildDatabase(context).also{ instance = it}
            }
        }


        private fun buildDatabase(context: Context):AppDatabase{
            return Room.databaseBuilder(context,AppDatabase::class.java,DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //TODO 当数据库创建成功，需要将本地mock的JSON数据插入
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    })
                    .build()
        }
    }
}