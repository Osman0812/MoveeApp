package com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel

import com.example.myapplication.data.model.actormoviecreditsmodel.ActorMovieCastDto
import com.example.myapplication.data.model.actormoviecreditsmodel.ActorMovieCrewDto

data class ActorMovieCreditUi(
    val cast: List<ActorMovieCastDto>,
    val crew: List<ActorMovieCrewDto>,
    val id: Int
)
