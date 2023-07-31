package com.example.myapplication.data.repository


import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.SafeApiRequest
import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.SessionResponse
import com.example.myapplication.data.remote.model.ValidationCreateRequest
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.remote.service.TMDbApi
import com.example.myapplication.util.extension.ApiResult
import retrofit2.Response
import javax.inject.Inject


class AuthRepository @Inject constructor(private val tmdbApi: TMDbApi) : SafeApiRequest() {
    suspend fun createRequestToken(): ApiResult<RequestTokenResponse> {
        return apiRequest { tmdbApi.createRequestToken("Bearer " + BuildConfig.TMDB_API_KEY) }
    }
    suspend fun validateRequestToken(
        user: ValidationRequest,
        requestToken: String
    ): ApiResult<RequestTokenResponse> {
        return apiRequest {
            tmdbApi.validateRequestToken(
                "Bearer " + BuildConfig.TMDB_API_KEY,
                user,
                requestToken
            )
        }
    }
    suspend fun createSessionId(validatedToken: ValidationCreateRequest): ApiResult<SessionResponse> {
        return apiRequest {
            tmdbApi.createSessionId(
                "Bearer " + BuildConfig.TMDB_API_KEY,
                validatedToken
            )
        }
    }
}