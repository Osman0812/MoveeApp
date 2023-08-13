package com.example.myapplication.data.repository

import com.example.myapplication.data.model.actormodel.ActorDetailsModel
import com.example.myapplication.data.model.tvseriescreditsmodel.TvSeriesCreditsModel
import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.remote.service.ActorService
import com.example.myapplication.util.state.ApiResult
import javax.inject.Inject

class ActorRepository @Inject constructor(private val actorApi: ActorService) : SafeApiRequest() {
    suspend fun getActorDetails(personId: Int): ApiResult<ActorDetailsModel> {
        return apiRequest { actorApi.getActorsDetails(personId) }
    }
}