package com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel

import com.example.myapplication.data.model.actortvcreditsmodel.CastDto
import com.example.myapplication.data.model.actortvcreditsmodel.CrewDto

data class ActorTVCreditUi(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
)
