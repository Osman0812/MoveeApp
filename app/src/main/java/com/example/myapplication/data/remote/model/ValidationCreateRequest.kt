package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class ValidationCreateRequest(
    @SerializedName("request_token") val requestToken: String
)