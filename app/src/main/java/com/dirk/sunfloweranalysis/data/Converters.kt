package com.dirk.sunfloweranalysis.data

import androidx.room.TypeConverter
import java.util.*

/**
 * 类型转化器
 */
class Converters{
    @TypeConverter fun calenderToDatestamp(calendar: Calendar):Long = calendar.timeInMillis

    @TypeConverter fun dataStampToCalender(value:Long) = Calendar.getInstance().apply {
        timeInMillis = value
    }
}