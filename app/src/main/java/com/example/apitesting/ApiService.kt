package com.example.apitesting

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/empregister") // Replace with your actual endpoint URL
    fun registerUser(@Body registrationData: JsonObject): Call<Void>

    @POST("/emplogin")
    fun loginRequest(@Body credentials: LoginData): Call<RegistrationActivity>

    @GET("/get/{id}")
    fun getAdminById(@Path("id") id: String): Call<RegistrationActivity>

    @GET("/getAdmin")
    fun getAllAdmins(): Call<List<RegistrationActivity>>

}
