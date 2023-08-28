package com.example.myapplication.data.model.actortvcreditsmodel

import com.google.gson.annotations.SerializedName

data class ActorTvCastDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("episode_count")
    val episodeCount: Int,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
)