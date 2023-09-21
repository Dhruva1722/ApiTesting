package com.example.apitesting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    private lateinit var newUserTextView: TextView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var passResetBtn: TextView

    private val gson = Gson()

    private lateinit var apiService: ApiService

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        emailEditText = findViewById(R.id.loginEmailID)
        passwordEditText = findViewById(R.id.loginPasswordID)
        loginButton = findViewById(R.id.loginBtnID)
        newUserTextView = findViewById(R.id.newUserID)
        passResetBtn = findViewById(R.id.forgetPAsswordID)


        apiService = RetrofitClient.getClient().create(ApiService::class.java)




        newUserTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        // User login function
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val credentials = JsonObject().apply {
            addProperty("email_address", email)
            addProperty("password", password)
        }

        val authRepository = AuthRepository()
        authRepository.loginUser(credentials) { result ->
            when (result) {
                is Result.Success -> {
                    // Login successful
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                is Result.Error -> {
                    // Handle login error, e.g., show an error message
                    Toast.makeText(applicationContext, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
//        loginButton.setOnClickListener {
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//
//            val loginAdmin = LoginData(email, password)
//            val adminDataJson = gson.toJsonTree(loginAdmin).asJsonObject
//            Log.d("-----------", "onCreate: user data"+adminDataJson)
//            val call = apiService.loginRequest(adminDataJson)
//            call.enqueue(object : Callback<Any> {
//                override fun onResponse(call: Call<Any>, response: Response<Any>) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(
//                            this@LoginActivity,
//                            "login Succeccful",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        val intent = Intent(applicationContext, MainActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(
//                            this@LoginActivity,
//                            "login fail",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Any>, t: Throwable) {
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "login network error",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//        }
//        }
//    }
//
//private fun <T> Call<T>.enqueue(callback: Callback<Any>) {
//
//}
//
//
//data class LoginData(
//    val email : String,
//    val password :String
//)

