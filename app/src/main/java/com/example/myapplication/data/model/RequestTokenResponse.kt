package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    val success: Boolean,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("expires_at") val expiresAt: String
)
