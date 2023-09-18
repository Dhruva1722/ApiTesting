package com.example.apitesting

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST


interface ApiService {

    @POST("/empregister")
    fun registerUser(@Body registrationData: JsonObject): Call<Any>


    @POST("/emplogin")
    fun loginRequest(@Body credentials: JsonObject): Call<Any>

    @GET("/get")
    fun getEmployees(): Call<List<Employee>>

    @GET("/getAdmin")
    fun getAllAdmins(): Call<List<RegistrationActivity>>
}