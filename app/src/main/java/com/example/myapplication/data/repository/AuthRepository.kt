package com.example.myapplication.data.repository



import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.model.authmodel.RequestTokenResponseDto
import com.example.myapplication.data.model.authmodel.SessionResponseDto
import com.example.myapplication.data.model.authmodel.ValidationCreateRequest
import com.example.myapplication.data.model.authmodel.ValidationRequest
import com.example.myapplication.data.remote.service.AuthService
import com.example.myapplication.util.state.ApiResult
import javax.inject.Inject


class AuthRepository @Inject constructor(private val tmdbApi: AuthService) : SafeApiRequest() {
    suspend fun createRequestToken(): ApiResult<RequestTokenResponseDto> {
        return apiRequest { tmdbApi.createRequestToken() }
    }
    suspend fun validateRequestToken(
        user: ValidationRequest,
        requestToken: String
    ): ApiResult<RequestTokenResponseDto> {
        return apiRequest {
            tmdbApi.validateRequestToken(
                user,
                requestToken
            )
        }
    }
    suspend fun createSessionId(validatedToken: ValidationCreateRequest): ApiResult<SessionResponseDto> {
        return apiRequest {
            tmdbApi.createSessionId(
                validatedToken
            )
        }
    }
}