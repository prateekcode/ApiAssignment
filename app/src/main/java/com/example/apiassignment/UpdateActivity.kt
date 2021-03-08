package com.example.apiassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.apiassignment.model.Data
import com.example.apiassignment.repository.Repository
import com.example.apiassignment.viewmodel.MainViewModel
import com.example.apiassignment.viewmodel.MainViewModelFactory
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.single_item_layout.*

class UpdateActivity : AppCompatActivity() {

    private lateinit var avatar: CircleImageView
    private lateinit var firstname: EditText
    private lateinit var lastname: EditText
    private lateinit var email: EditText
    private lateinit var updateBtn: Button
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val data = intent.getStringExtra("userId")
        supportActionBar?.title = "Update The User"

        avatar = circleImageView
        firstname = editTextFirstName
        lastname = editTextLastName
        email = editTextEmail
        updateBtn = updateButton

        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        if (data != null) {
            viewModel.getSingleUser(data.toInt())
            viewModel.singleResponse.observe(this, Observer {
                if (it.isSuccessful) {
                    Log.d("Response", it.code().toString())
                    avatar.load(it.body()?.data?.avatar)
                    (firstname as TextView).text = it.body()?.data?.first_name
                    (lastname as TextView).text = it.body()?.data?.last_name
                    (email as TextView).text = it.body()?.data?.email
                } else {
                    Log.d("Response", it.code().toString())
                }
            })
        }

        updateBtn.setOnClickListener {
            if (data != null) {
                updateUser(firstname.toString(), lastname.toString(), email.toString(), data.toInt())
            }
        }

    }

    private fun updateUser(
        first_name: String,
        last_name: String,
        email_Address: String,
        userId: Int
    ) {
        val newUser =
            Data("", email_Address, first_name, userId, last_name)
        viewModel.updateUser(userId, newUser)
        viewModel.updateUserResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Updated", response.code().toString())
                Log.d("Updated", response.body().toString())
                Log.d("Updated", response.message())
                Toast.makeText(
                    applicationContext,
                    "User updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                onBackPressed()
            } else {
                Log.d("Updated", response.code().toString())
                Log.d("Updated", response.message())
                Toast.makeText(
                    applicationContext,
                    response.errorBody().toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}