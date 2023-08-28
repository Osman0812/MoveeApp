package com.example.myapplication.data.model.actormoviecreditsmodel

data class ActorMovieCredits(
    val cast: List<ActorMovieCastDto>,
    val crew: List<ActorMovieCrewDto>,
    val id: Int
)