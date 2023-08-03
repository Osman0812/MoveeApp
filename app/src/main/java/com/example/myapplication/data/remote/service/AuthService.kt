package com.example.myapplication.data.remote.service


import com.example.myapplication.data.model.RequestTokenResponse
import com.example.myapplication.data.model.SessionResponse
import com.example.myapplication.data.model.ValidationCreateRequest
import com.example.myapplication.data.model.ValidationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthService {
    @GET(Constants.NEW_REQUEST_TOKEN_PATH)
    suspend fun createRequestToken(
    ): Response<RequestTokenResponse>
    @POST(Constants.LOGIN_VALIDATE_PATH)
    suspend fun validateRequestToken(
        @Body user: ValidationRequest,
        @Query("request_token") requestToken: String
    ): Response<RequestTokenResponse>
    @POST(Constants.GET_SESSION_ID_PATH)
    suspend fun createSessionId(
        @Body session: ValidationCreateRequest
    ): Response<SessionResponse>
}


