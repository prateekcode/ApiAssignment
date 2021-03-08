package com.example.apiassignment.repository

import com.example.apiassignment.api.RetrofitInstance
import com.example.apiassignment.model.Data
import com.example.apiassignment.model.NewData
import com.example.apiassignment.model.Users
import retrofit2.Response

class Repository {

    suspend fun getSingleUser(userId:Int): Response<NewData> {
        return RetrofitInstance.api.getSingleUser(userId)
    }

    suspend fun getUsersList(pageNumber:Int): Response<Users>{
        return RetrofitInstance.api.getUsersList(pageNumber)
    }

    suspend fun createUser(users: Data): Response<Data>{
        return RetrofitInstance.api.createUser(users)
    }

    suspend fun updateUser(userId: Int, users: Data): Response<Data>{
        return RetrofitInstance.api.updateUser(userId, users)
    }

    suspend fun deleteUser(userId: Int): Response<Data>{
        return RetrofitInstance.api.deleteUser(userId)
    }
}