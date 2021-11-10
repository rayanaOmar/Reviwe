package com.example.includesallideas.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

//this interface will add all operation i need to to on my database
@Dao
interface UserDao {
    //get me all the rows and arrangement by the title ASC
    @Query("SELECT * FROM User ORDER BY id ASC")
    fun getAllUserInfo(): LiveData<List<UserEntity>>

    @Insert
    fun insertUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

}