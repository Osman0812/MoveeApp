package com.example.myapplication.data.model.genresmodel

import com.google.gson.annotations.SerializedName
data class GenresDto(
    @SerializedName("genres")
    val genres: List<GenreDto>
)