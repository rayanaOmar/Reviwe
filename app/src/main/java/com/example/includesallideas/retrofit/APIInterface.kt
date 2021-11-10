package com.example.includesallideas.retrofit

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    //get the user info
    @GET("/test/")
    fun getAllUsers(): Call<List<Users>>

    //add new user
    @POST("/test/")
    fun addUser(@Body user: Users): Call<Users>

    //update the user info
    @PUT("/test/{id}")
    fun updateUser(@Path("id") id : Int, @Body userData: Users): Call<Users>

    //delete the user
    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id : Int ): Call<Void>






}