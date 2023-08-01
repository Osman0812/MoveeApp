package com.example.myapplication.data.repository



import com.example.myapplication.data.remote.SafeApiRequest
import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.SessionResponse
import com.example.myapplication.data.remote.model.ValidationCreateRequest
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.remote.service.TMDbApi
import com.example.myapplication.util.extension.ApiResult
import javax.inject.Inject


class AuthRepository @Inject constructor(private val tmdbApi: TMDbApi) : SafeApiRequest() {
    suspend fun createRequestToken(): ApiResult<RequestTokenResponse> {
        return apiRequest { tmdbApi.createRequestToken() }
    }
    suspend fun validateRequestToken(
        user: ValidationRequest,
        requestToken: String
    ): ApiResult<RequestTokenResponse> {
        return apiRequest {
            tmdbApi.validateRequestToken(
                user,
                requestToken
            )
        }
    }
    suspend fun createSessionId(validatedToken: ValidationCreateRequest): ApiResult<SessionResponse> {
        return apiRequest {
            tmdbApi.createSessionId(
                validatedToken
            )
        }
    }
}