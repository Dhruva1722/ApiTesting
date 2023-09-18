package com.example.apitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1)
        listView.adapter = adapter

        //fetching data from api
        val apiService = RetrofitClient.getClient().create(ApiService::class.java)
        val call = apiService.getEmployees() //this function in api service

        call.enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>) =
                if (response.isSuccessful) {
                    val employees = response.body()
                    if (employees != null) {
                        for (employee in employees) {
                            // you can add as many as fields as you want for trial i just added name email & contact
                            adapter.add(employee.Emp_name)
                            adapter.add(employee.Emp_email)
                            adapter.add(employee.Emp_contact_No)

                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        // error if data is not there
                        Toast.makeText(this@MainActivity, "No data available", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "API request failed", Toast.LENGTH_SHORT).show()
                }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })


    }
}