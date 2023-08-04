package com.example.myapplication.data.model.authmodel

import com.google.gson.annotations.SerializedName

data class ValidationRequest(
    @SerializedName("username") var username: String,
    @SerializedName("password") val password: String,
)
