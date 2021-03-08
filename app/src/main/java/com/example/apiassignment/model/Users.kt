package com.example.apiassignment.model

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("data")
    val myData: List<Data>,
    val page: Int,
    val per_page: Int,
    val support: Support,
    val total: Int,
    val total_pages: Int
)