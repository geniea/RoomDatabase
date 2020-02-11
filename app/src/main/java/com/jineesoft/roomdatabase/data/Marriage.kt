package com.jineesoft.roomdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "marriage_table")
data class Marriage(
    @PrimaryKey(autoGenerate = true)
    var marriageId: Int,
    @ColumnInfo(name = "marriage_date")
    var marriageDate: Date

)