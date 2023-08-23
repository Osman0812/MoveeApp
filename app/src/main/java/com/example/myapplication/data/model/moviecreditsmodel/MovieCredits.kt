package com.example.myapplication.data.model.moviecreditsmodel

import com.google.gson.annotations.SerializedName

data class MovieCreditsDto(
    @SerializedName("cast")
    val cast: List<CastDto>,
    @SerializedName("crew")
    val crew: List<CrewDto>,
    @SerializedName("id")
    val id: Int
)