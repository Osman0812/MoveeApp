package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.actormodel.ActorDetailsModel
import com.example.myapplication.data.model.actormoviecreditsmodel.ActorMovieCredits
import com.example.myapplication.data.model.actortvcreditsmodel.ActorTvCredits
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ActorService {
    @GET(Constants.ACTOR_DETAIL)
    suspend fun getActorsDetails(
        @Path("person_id") personId: Int
    ): Response<ActorDetailsModel>

    @GET(Constants.ACTOR_TV_SERIES_CREDITS)
    suspend fun getActorTVCredits(
        @Path("person_id") personId: Int
    ): Response<ActorTvCredits>

    @GET(Constants.ACTOR_MOVIES_CREDITS)
    suspend fun getActorMovieCredits(
        @Path("person_id") personId: Int
    ): Response<ActorMovieCredits>
}