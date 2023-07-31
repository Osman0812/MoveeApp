package com.example.myapplication.data.remote.model

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val session_id: String
    )
