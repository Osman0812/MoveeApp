package com.example.myapplication.data.model.moviecreditsmodel

import com.google.gson.annotations.SerializedName

data class MovieCreditsDto(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)