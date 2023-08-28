package com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel

import com.example.myapplication.data.model.actortvcreditsmodel.ActorTvCastDto
import com.example.myapplication.data.model.actortvcreditsmodel.ActorTvCrewDto

data class ActorTVCreditUi(
    val cast: List<ActorTvCastDto>,
    val crew: List<ActorTvCrewDto>,
    val id: Int
)
