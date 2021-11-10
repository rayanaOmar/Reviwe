package com.example.includesallideas.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.includesallideas.roomDatabase.UserDatabase
import com.example.includesallideas.roomDatabase.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val userInfo: LiveData<List<UserEntity>>
    private val databaseObject: UserDatabase

    init {
        databaseObject = UserDatabase.getInstant(application)
        userInfo = databaseObject.UserDao().getAllUserInfo()
    }

    fun getAllUserInfo(): LiveData<List<UserEntity>> {
        return userInfo
    }

    fun addUser(id:Int, name: String,location:String) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseObject.UserDao().insertUser(UserEntity(id,name,location))
        }
    }
    fun deleteUser(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseObject.UserDao().deleteUser(UserEntity(id))
        }
    }
}