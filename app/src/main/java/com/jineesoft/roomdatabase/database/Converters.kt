package com.jineesoft.roomdatabase.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    /*
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return value.let{ Date(it) }
    }
    fun dateToTimestamp(date: Date): Long {
        return date.time.toLong()
    }
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}