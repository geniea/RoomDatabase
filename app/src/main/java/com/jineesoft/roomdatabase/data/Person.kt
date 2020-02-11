package com.jineesoft.roomdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey(autoGenerate = true)
    var personId: Int,
    @ColumnInfo(name="first_name")
    var firstName: String,
    @ColumnInfo(name="last_name")
    var lastName: String,
    @ColumnInfo(name="gender")
    var gender: Boolean
)