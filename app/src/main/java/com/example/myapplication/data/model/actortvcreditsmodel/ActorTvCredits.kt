package com.example.myapplication.data.model.actortvcreditsmodel

data class ActorTvCredits(
    val cast: List<ActorTvCastDto>,
    val crew: List<ActorTvCrewDto>,
    val id: Int
)