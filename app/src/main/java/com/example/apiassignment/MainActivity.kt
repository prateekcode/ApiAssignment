package com.example.apiassignment

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiassignment.adapter.UsersAdapter
import com.example.apiassignment.model.Data
import com.example.apiassignment.repository.Repository
import com.example.apiassignment.viewmodel.MainViewModel
import com.example.apiassignment.viewmodel.MainViewModelFactory
import com.example.apiassignment.views.CustomUserDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CustomUserDialog.ExampleDialogListener,
    UsersAdapter.OnItemClickListener {
    private lateinit var viewModel: MainViewModel
    private val repository = Repository()
    private val userAdapter by lazy { UsersAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (checkInternet()){
            setUpRecyclerView()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.getUserList(1)
            viewModel.userListResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.let { it -> userAdapter.setData(it.myData) }
                    Log.d("Response", response.code().toString())
                } else {
                    Log.d("Response", response.code().toString())
                }
            })

            create_user_btn.setOnClickListener {
                openDialog("Create User", "Create")
            }
        }else{
            Toast.makeText(applicationContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUser(first_name: String, last_name: String, email_Address: String) {
        val random = (0..10000).random()
        val newUser =
            Data("", email_Address.toString(), first_name.toString(), random, last_name.toString())
        viewModel.createUser(newUser)
        viewModel.createUserResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Created", response.code().toString())
                Log.d("Created", response.body().toString())
                Log.d("Created", response.message())
                Toast.makeText(
                    applicationContext,
                    "$first_name is added to list",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d("Created", response.code().toString())
                Log.d("Created", response.message())
                Toast.makeText(
                    applicationContext,
                    response.errorBody().toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



    private fun setUpRecyclerView() {
        user_list_rv.adapter = userAdapter
        user_list_rv.layoutManager = LinearLayoutManager(this)
    }

    private fun openDialog(title: String, action: String) {
        val createUserDialog = CustomUserDialog(title, action)
        createUserDialog.show(supportFragmentManager, "xcreate")
    }

    override fun applyTexts(firstName: String?, lastName: String?, emailAddress: String?) {
        createUser(firstName.toString(), lastName.toString(), emailAddress.toString())
    }

    override fun onClick(position: Int) {
        val userId = position+1
        val intent = Intent(this, UpdateActivity::class.java)
        intent.putExtra("userId", userId.toString())
        startActivity(intent)
    }

    override fun onLongClick(position: Int) {
        val userId = position + 1
        viewModel.deleteUser(userId)
        viewModel.deleteUserResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Deleted", response.code().toString())
                Log.d("Deleted", response.message())
                Toast.makeText(
                    applicationContext, "User deleted successfully with Response Code ${
                        response.code()
                    }", Toast.LENGTH_SHORT
                ).show()
            } else {
                Log.d("Deleted", response.code().toString())
                Log.d("Deleted", response.message())
            }
        })
    }

    //Checking Internet Connection
    fun checkInternet(): Boolean{
        val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}