package com.example.myapplication.data.repository


import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.remote.service.TMDbApi
import retrofit2.Response
import javax.inject.Inject


class AuthRepository @Inject constructor(private val tmdbApi: TMDbApi) {
    suspend fun createRequestToken(): Response<RequestTokenResponse> {
        return tmdbApi.createRequestToken("Bearer " + BuildConfig.TMDB_API_KEY)
    }

    suspend fun validateRequestToken(requestToken: ValidationRequest): Response<RequestTokenResponse> {
        return tmdbApi.validateRequestToken("Bearer " + BuildConfig.TMDB_API_KEY,requestToken)
    }
/*
    suspend fun createSessionId(apiKey: String, requestToken: String): SessionResponse {
        return tmdbApi.createSessionId(apiKey, requestToken)
    }

 */
}