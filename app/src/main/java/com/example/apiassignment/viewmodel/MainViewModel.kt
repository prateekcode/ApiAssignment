package com.example.apiassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiassignment.model.Data
import com.example.apiassignment.model.NewData
import com.example.apiassignment.model.Users
import com.example.apiassignment.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val singleResponse: MutableLiveData<Response<NewData>> = MutableLiveData()
    val userListResponse: MutableLiveData<Response<Users>> = MutableLiveData()
    val createUserResponse:MutableLiveData<Response<Data>> = MutableLiveData()
    val updateUserResponse: MutableLiveData<Response<Data>> = MutableLiveData()
    val deleteUserResponse: MutableLiveData<Response<Data>> = MutableLiveData()

    fun getSingleUser(userId: Int){
        viewModelScope.launch {
            val response = repository.getSingleUser(userId)
            singleResponse.value = response
        }
    }

    fun getUserList(pageNumber: Int){
        viewModelScope.launch {
            val response = repository.getUsersList(pageNumber)
            userListResponse.value = response
        }
    }

    fun createUser(users: Data){
        viewModelScope.launch {
            val response = repository.createUser(users)
            createUserResponse.value = response
        }
    }

    fun updateUser(userId: Int, users: Data){
        viewModelScope.launch {
            val response = repository.updateUser(userId, users)
            updateUserResponse.value = response
        }
    }

    fun deleteUser(userId: Int){
        viewModelScope.launch {
            val response = repository.deleteUser(userId)
            deleteUserResponse.value = response
        }
    }
}