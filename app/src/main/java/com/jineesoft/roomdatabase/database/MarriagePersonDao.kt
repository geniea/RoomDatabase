package com.jineesoft.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jineesoft.roomdatabase.data.MarriagePerson

@Dao
interface MarriagePersonDao {
    @Insert
    fun insert(marriagePerson: MarriagePerson)

    @Query("SELECT * from marriage_person_table where marriagePersonID = :key ")
    fun get(key: Int): MarriagePerson?
}