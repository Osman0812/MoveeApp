package com.example.myapplication.data.remote.model

data class RequestTokenResponse(
    val success: Boolean,
    val request_token: String,
    val expires_at: String
    )
