package com.example.myapplication.data.model.authmodel

import com.google.gson.annotations.SerializedName

data class RequestTokenResponseDto(
    val success: Boolean,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("expires_at") val expiresAt: String
)