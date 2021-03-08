package com.example.apiassignment.api

import com.example.apiassignment.model.Data
import com.example.apiassignment.model.NewData
import com.example.apiassignment.model.Users
import retrofit2.Response
import retrofit2.http.*

interface SimpleAPI {

    @GET("api/users/{userId}")
    suspend fun getSingleUser(
        @Path("userId") id:Int
    ): Response<NewData>

    @GET("api/users")
    suspend fun getUsersList(
        @Query("page") page: Int
    ): Response<Users>

    @POST("api/users")
    suspend fun createUser(
        @Body newUser: Data
    ): Response<Data>

    @PUT("api/users/{userId}")
    suspend fun updateUser(
        @Path("userId") id: Int,
        @Body updateUser: Data
    ): Response<Data>

    @DELETE("api/users/{userId}")
    suspend fun deleteUser(
        @Path("userId") id: Int
    ): Response<Data>
}