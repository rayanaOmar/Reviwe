package com.example.includesallideas.roomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User") // name of the table of database
data class UserEntity (
    @PrimaryKey(autoGenerate = true) // the primary key of the row
    //the 3 column in my database
    @ColumnInfo(name = "id")val id: Int = 0,
    @ColumnInfo(name = "name")val name: String = "",
    @ColumnInfo(name = "location")val location: String = ""

)