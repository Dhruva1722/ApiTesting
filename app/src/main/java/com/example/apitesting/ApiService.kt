package com.example.apitesting

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/empregister")
    fun registerUser(@Body registrationData: RegistrationData): Call<Any>
}
