package com.example.myapplication.data.model.singletvmodel

import com.google.gson.annotations.SerializedName
data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)