package com.example.myapplication.data.remote.service

import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.SessionResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TMDbApi {
    //api key = f2f4bcd525ac9cb9ef55fb2697cfc39b
    @GET("auth/token/new")
    suspend fun getRequestToken(
        @Query("f2f4bcd525ac9cb9ef55fb2697cfc39b") apiKey: String
    ): RequestTokenResponse
    @POST("auth/session/new")
    suspend fun createSession(
        @Field("f2f4bcd525ac9cb9ef55fb2697cfc39b") apiKey: String,
        @Field("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMmY0YmNkNTI1YWM5Y2I5ZWY1NWZiMjY5N2NmYzM5YiIsInN1YiI6IjY0YjRmNmE0Nzg1NzBlMDBhZDRjNTkwOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GUpZ0k36wGv1895h_LgX47qEREGXEDNoM4hEuByCUmk") requestToken: String
    ): SessionResponse


}