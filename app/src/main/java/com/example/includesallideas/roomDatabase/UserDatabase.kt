package com.example.includesallideas.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class],version = 1,exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    companion object{
        var instant: UserDatabase?=null
        fun getInstant(context: Context): UserDatabase {
            if(instant !=null)
            {
                return instant as UserDatabase
            }
            instant = Room.databaseBuilder(context, UserDatabase::class.java,"name").run{
                allowMainThreadQueries() }.build()
            return instant as UserDatabase
        }
    }

    abstract fun UserDao(): UserDao;
}