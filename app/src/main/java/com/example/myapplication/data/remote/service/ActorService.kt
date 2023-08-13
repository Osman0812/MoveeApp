package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.actormodel.ActorDetailsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorService {
    @GET(Constants.ACTOR_DETAIL)
    suspend fun getActorsDetails(
        @Path("person_id") personId: Int
    ): Response<ActorDetailsModel>
}