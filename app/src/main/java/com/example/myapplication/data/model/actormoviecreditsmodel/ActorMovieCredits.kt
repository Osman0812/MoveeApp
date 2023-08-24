package com.example.myapplication.data.model.actormoviecreditsmodel

data class ActorMovieCredits(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)