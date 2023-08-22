package com.example.myapplication.data.model.tvseriescreditsmodel

import com.google.gson.annotations.SerializedName

data class TvSeriesCreditsDto(
    @SerializedName("cast")
    val cast: List<CastDto>,
    @SerializedName("crew")
    val crew: List<CrewDto>,
    @SerializedName("id")
    val id: Int
)