package com.example.apitesting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val Any.message: Any
    get() {
        return true
    }
private val Any.status: Any
    get() {
        return true
    }
private val <T> Call<T>.isSuccessful: Boolean
    get() {return true}

class RegistrationActivity : AppCompatActivity() {

    private lateinit var haveAnAccount: TextView


    private lateinit var apiService: ApiService
    private val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

       apiService = RetrofitClient.getClient().create(ApiService::class.java)


        apiService = Retrofit.Builder()
//            .baseUrl("https://dashboardbackend-production-9839.up.railway.app/") // Replace with your API base URL
              .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        val registerButton = findViewById<Button>(R.id.registerBtn)
        val registerId = findViewById<TextInputEditText>(R.id.registerID)
        val registerEmail = findViewById<TextInputEditText>(R.id.registerEmailID)
        val registerName = findViewById<TextInputEditText>(R.id.registerNameID)
        val registerPassword = findViewById<TextInputEditText>(R.id.registerPasswordID)


        registerButton.setOnClickListener {
            val _id = registerId.text.toString()
            val email = registerEmail.text.toString()
            val name = registerName.text.toString()
            val password = registerPassword.text.toString()


            val registrationData = JsonObject().apply {
                addProperty("_id", _id)
                addProperty("name", name)
                addProperty("email", email)
                addProperty("password", password)

            }

            val response = apiService.registerUser(registrationData)
//          val adminData = AdminData(_id, email,name, password)

            if (response.isSuccessful) {
                // Registration was successful, handle the success message
                val registrationResponse = response.body()
                if (registrationResponse != null) {
                    if (registrationResponse.status == "Success") {
                        // Registration was successful, handle the success message
//                        Toast.makeText(this@RegistrationActivity,
//                            registrationResponse.message,
//                            Toast.LENGTH_SHORT
//                        ).show()

                        Log.d("----------" ,"onCreate: successfull")

                        // Redirect to the login page or perform other actions as needed
                    } else {
                        // Registration failed, handle the error message
//                        Toast.makeText(
//                            this@RegistrationActivity,
//                            registrationResponse.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
                        Log.d("----------" ,"onCreate: fail")
                    }
                }
            } else {
                // Registration request failed, handle the error
                Toast.makeText(
                    this@RegistrationActivity,
                    "Registration failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

private fun <T> Call<T>.body(): Any {
return true
}

data class AdminData(
    val _id: String?,
    val email: String?,
    val name: String?,
    val password:String?
)




