package com.jineesoft.roomdatabase.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jineesoft.roomdatabase.data.Person

@Dao
interface PersonDao {
    @Insert
    fun insert(person:Person)

    @Query("SELECT * from person_table where personId = :key")
    fun get(key: Int): Person?

    @Query("SELECT * from person_table")
    fun getAll(): List<Person>
}