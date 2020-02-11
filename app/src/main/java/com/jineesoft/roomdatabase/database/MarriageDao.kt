package com.jineesoft.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jineesoft.roomdatabase.data.Marriage

@Dao
interface MarriageDao{
    @Insert
    fun insert(marriage: Marriage)
    @Query("SELECT * from marriage_table where marriageId = :key ")
    fun get(key: Int): Marriage?
}