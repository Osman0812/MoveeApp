package com.example.myapplication.data.model.actortvcreditsmodel

data class ActorTvCredits(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)