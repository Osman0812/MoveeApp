package com.example.myapplication.data.model.singletvmodel

import com.google.gson.annotations.SerializedName

data class NetworkDto(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)