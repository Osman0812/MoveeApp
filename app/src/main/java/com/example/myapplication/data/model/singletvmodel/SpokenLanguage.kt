package com.example.myapplication.data.model.singletvmodel

import com.google.gson.annotations.SerializedName
data class SpokenLanguageDto(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String
)