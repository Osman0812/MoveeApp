package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class ValidationCreateRequest(
    @SerializedName("request_token") val requestToken: String
)