package com.jineesoft.roomdatabase.data

import androidx.room.*
import androidx.room.ForeignKey.RESTRICT

@Entity(tableName = "marriage_person_table",
    indices = [Index("marriage_id"), Index("person_id")],
    foreignKeys = [ForeignKey(entity = Marriage::class,
        parentColumns = arrayOf("marriageId"),
        childColumns = arrayOf("marriage_id"),
        onDelete = RESTRICT ),
        ForeignKey( entity = Person::class,
            parentColumns = arrayOf( "personId"),
            childColumns = arrayOf("person_id"),
            onDelete = RESTRICT ) ]
    )
data class MarriagePerson(
    @PrimaryKey(autoGenerate = true)
    var marriagePersonID: Int,
    @ColumnInfo(name = "marriage_id")
    val marriageId: Int,
    @ColumnInfo(name ="person_id")
    val personId: Int
)