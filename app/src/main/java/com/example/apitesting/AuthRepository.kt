package com.example.apitesting

import android.service.autofill.UserData
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.firstOrNull
import kotlin.coroutines.jvm.internal.CompletedContinuation.context


class AuthRepository {
        private val authApiService = RetrofitClient.create(ApiService::class.java)
        private val tokenManager = TokenManager(context)



    suspend fun fetchUserData(): Result<UserData> {
        val token = tokenManager.getToken().firstOrNull()
        return if (token != null) {
            try {
                // Add the token to the request headers
                val headers = mapOf("Authorization" to "Bearer $token")
                val response = authApiService.getUserData(headers)

                if (response.isSuccessful) {
                    val userData = response.body()
                    if (userData != null) {
                        Result.Success(userData)
                    } else {
                        Result.Error("Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Result.Error("Request failed: $errorBody")
                }
            } catch (e: Exception) {
                Result.Error("Request exception: ${e.message}")
            }
        } else {
            Result.Error("No token available")
        }
    }

        suspend fun loginUser(credentials: JsonObject, callback: (Result<Any>) -> Unit) {
            val result = try {
                val response = authApiService.login(credentials)

                if (response.isSuccessful) {
                    val token = response.headers()["Authorization"]
                    if (token != null) {
                        tokenManager.saveToken(token)
                    }
                    Result.Success(response.body())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Result.Error("Login failed: $errorBody")
                }
            } catch (e: Exception) {
                Result.Error("Login exception: ${e.message}")
            }
            callback(result)
        }
}

private fun Result.Companion.Error(s: String): Result<UserData> {

    return TODO("Provide the return value")
}

private fun Result.Companion.Success(userData: Any): Result<UserData> {
    TODO("Not yet implemented")
}


