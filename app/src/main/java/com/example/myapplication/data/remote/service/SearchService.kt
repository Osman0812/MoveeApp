package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.searchmodel.SearchModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET(Constants.SEARCH_MULTI_PATH)
    suspend fun searchMulti(
        @Query("page") page: Int,
        @Query("query") query: String
    ): retrofit2.Response<SearchModel>
}