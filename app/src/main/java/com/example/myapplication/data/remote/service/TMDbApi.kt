package com.example.myapplication.data.remote.service



import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.SessionResponse
import com.example.myapplication.data.remote.model.ValidationCreateRequest
import com.example.myapplication.data.remote.model.ValidationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface TMDbApi {
    @GET("/3/authentication/token/new")
    suspend fun createRequestToken(
        @Header("Authorization") apiKey: String
    ): Response<RequestTokenResponse>
    @POST("/3/authentication/token/validate_with_login")
    suspend fun validateRequestToken(
        @Header("Authorization") apiKey: String,
        @Body user: ValidationRequest,
        @Query ("request_token") requestToken:String
    ): Response<RequestTokenResponse>
    @POST("/3/authentication/session/new")
    suspend fun createSessionId(
        @Header("Authorization") apiKey: String,
        @Body session : ValidationCreateRequest
    ): Response<SessionResponse>
}


