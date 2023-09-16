package com.example.apitesting

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitClient {
    private const val BASE_URL = "https://dashboardbackend-production-9839.up.railway.app/" // Replace with your actual server URL

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }


}

//object RetrofitClient {
//    private var retrofit: Retrofit? = null
//
//    fun getClient(baseUrl: String): Retrofit {
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return retrofit!!
//    }
//}