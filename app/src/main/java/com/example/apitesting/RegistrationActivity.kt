package com.example.apitesting

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationActivity : AppCompatActivity() {

    private lateinit var haveAnAccount: TextView


    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)



        apiService = RetrofitClient.getClient().create(ApiService::class.java)

        val registerButton = findViewById<Button>(R.id.registerBtn)
        val registerName = findViewById<TextInputEditText>(R.id.registerNameID)
        val registerEmail = findViewById<TextInputEditText>(R.id.registerEmailID)
        val registerPassword = findViewById<TextInputEditText>(R.id.registerPasswordID)

        registerButton.setOnClickListener {
            val name = registerName.text.toString()
            val email = registerEmail.text.toString()
            val password = registerPassword.text.toString()

            val registrationData = RegistrationData(name, email, password)

            // Make the API request
            val call = apiService.registerUser(registrationData)

            call.enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                                this@RegistrationActivity,
                                "Registration Succeccful",
                                Toast.LENGTH_SHORT
                            ).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                                this@RegistrationActivity,
                                "Registration fail",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Registration network error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

    }
}

data class RegistrationData(
    val name: String,
    val email: String,
    val password: String
)
