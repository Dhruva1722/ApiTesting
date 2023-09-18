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

    override fun onCreate(savedInstanceState: Bundle?) {
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


        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            val loginAdmin = LoginData(email, password)
            val adminDataJson = gson.toJsonTree(loginAdmin).asJsonObject
            Log.d("-----------", "onCreate: user data"+adminDataJson)
            val call = apiService.loginRequest(adminDataJson)
            call.enqueue(object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "login Succeccful",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "login fail",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "login network error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        }
    }

private fun <T> Call<T>.enqueue(callback: Callback<Any>) {

}


data class LoginData(
    val email : String,
    val password :String
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
