package com.example.myapplication.data.model.tvseriescreditsmodel

import com.google.gson.annotations.SerializedName

data class TvSeriesCreditsDto(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)