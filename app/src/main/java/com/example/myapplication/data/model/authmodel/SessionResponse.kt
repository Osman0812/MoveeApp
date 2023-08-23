package com.example.myapplication.data.model.authmodel

import com.google.gson.annotations.SerializedName

data class SessionResponseDto(
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionId: String
)