package com.example.myapplication.data.repository


import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.SessionResponse
import com.example.myapplication.data.remote.model.ValidationCreateRequest
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.remote.service.TMDbApi
import retrofit2.Response
import javax.inject.Inject


class AuthRepository @Inject constructor(private val tmdbApi: TMDbApi) {
    suspend fun createRequestToken(): Response<RequestTokenResponse> {
        return tmdbApi.createRequestToken("Bearer " + BuildConfig.TMDB_API_KEY)
    }
    suspend fun validateRequestToken(user:ValidationRequest, requestToken: String): Response<RequestTokenResponse> {
        return tmdbApi.validateRequestToken("Bearer " + BuildConfig.TMDB_API_KEY, user, requestToken)
    }
    suspend fun createSessionId(validatedToken: ValidationCreateRequest): Response<SessionResponse> {
        return tmdbApi.createSessionId("Bearer " + BuildConfig.TMDB_API_KEY,validatedToken)
    }
}