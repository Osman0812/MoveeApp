package com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel

import com.example.myapplication.data.model.actormoviecreditsmodel.CastDto
import com.example.myapplication.data.model.actormoviecreditsmodel.CrewDto

data class ActorMovieCreditUI(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)
