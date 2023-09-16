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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var newUserTextView: TextView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var passResetBtn: TextView



    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        emailEditText = findViewById(R.id.loginEmailID)
        passwordEditText = findViewById(R.id.loginPasswordID)
        loginButton = findViewById(R.id.loginBtnID)
        newUserTextView = findViewById(R.id.newUserID)
        passResetBtn = findViewById(R.id.forgetPAsswordID)


        apiService = RetrofitClient.getClient().create(ApiService::class.java)


        apiService = Retrofit.Builder()
            .baseUrl("https://dashboardbackend-production-9839.up.railway.app/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        newUserTextView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }


        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

// Create a login request object
            val loginAdmin = LoginData(email, password)

            apiService.loginRequest(loginAdmin).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            if (loginResponse.status == "Success") {
                                Toast.makeText(this@LoginActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@LoginActivity, "Registration fail", Toast.LENGTH_SHORT).show()
                                // Login failed, handle the error message
//                                val message = LoginResponse.message
                                // Display the error message to the user
                            }
                        }
                    } else {
                        // Login request failed, handle the error
                        // Display an error message to the user
                        Toast.makeText(this@LoginActivity, "Registration network error" , Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Handle network errors here
                    // Display a network error message to the user
                }
            })
        }
        }
    }

private fun <T> Call<T>.enqueue(callback: Callback<LoginResponse>) {

}

data class LoginData(
    val email : String,
    val password :String
)

data class LoginResponse(
    val status: String?,
    val token: String?, // Define the 'token' property here
    val message: String? // De
)

//        mAuth = FirebaseAuth.getInstance()
//
//
//        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE) // Initialize it here

//
//        val currentUser = mAuth.currentUser
//        if (currentUser != null || sharedPreferences.getBoolean("isLoggedIn", false)) {
//            navigateToMapsActivity()
//            return
//        }
//
//        loginButton.setOnClickListener {
//            loginUser()
//        }
//


//        passResetBtn.setOnClickListener {
//            val email = emailEditText.text.toString()
//
//            if (email.isNotEmpty()) {
//                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            // Password reset email sent successfully
//                            Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT)
//                                .show()
//                        } else {
//                            // Password reset email failed to send
//                            Toast.makeText(
//                                this,
//                                "Failed to send password reset email",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//            } else {
//                Toast.makeText(this, "Please enter your registered email", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }


//    private fun loginUser() {
//        val email = emailEditText.text.toString()
//        val password = passwordEditText.text.toString()
//
//        // Check if the email is valid
//        if (!isValidEmail(email)) {
//            emailEditText.error = "Invalid email address"
//            return
//        }
//
//        // Check if the password is valid (e.g., meets length requirements)
//        if (!isValidPassword(password)) {
//            passwordEditText.error = "Invalid password"
//            return
//        }
//
//        val currentUser = mAuth.currentUser
//        if (currentUser != null || sharedPreferences.getBoolean("isLoggedIn", false)) {
//
//            mAuth.currentUser?.reload()?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val isAccountDeleted = mAuth.currentUser == null
//                    if (isAccountDeleted) {
//                        // User's account has been deleted, clear preferences and show a message
//                        sharedPreferences.edit().clear().apply()
//                        Toast.makeText(
//                            this,
//                            "Your account has been deleted. Please sign up again.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        // Account still exists, navigate to MapsActivity
//                        Toast.makeText(
//                            this,
//                            "Pls Register first.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        navigateToMapsActivity()
//                    }
//                }
//            }
//        }

//        mAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // User login successful
//                    val user: FirebaseUser? = mAuth.currentUser
//                    if (user != null) {
//                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
//                        navigateToMapsActivity()
//                    }
//                } else {
//                    // User login failed
//                    Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
//                }
//            }
