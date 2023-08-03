package com.example.myapplication.data.repository



import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.model.RequestTokenResponse
import com.example.myapplication.data.model.SessionResponse
import com.example.myapplication.data.model.ValidationCreateRequest
import com.example.myapplication.data.model.ValidationRequest
import com.example.myapplication.data.remote.service.AuthService
import com.example.myapplication.util.state.ApiResult
import javax.inject.Inject


class AuthRepository @Inject constructor(private val tmdbApi: AuthService) : SafeApiRequest() {
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