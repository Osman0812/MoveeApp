package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.SessionResponse
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.remote.service.TMDbApi
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _requestToken = MutableLiveData<Response<RequestTokenResponse>>()
    val requestToken: LiveData<Response<RequestTokenResponse>> = _requestToken

    private val _validationResponse = MutableLiveData<Response<RequestTokenResponse>>()
    val validationResponse: LiveData<Response<RequestTokenResponse>> = _validationResponse

    private val _sessionId = MutableLiveData<SessionResponse>()
    val sessionId: LiveData<SessionResponse> = _sessionId

    fun createRequestToken() {
        viewModelScope.launch {
            val result = authRepository.createRequestToken()
            _requestToken.value = result
        }
    }
    fun performValidation(username: String, password: String, token: String) {
        viewModelScope.launch {
            try {
                val user = ValidationRequest(username, password, token)
                val response = authRepository.validateRequestToken(user)
                if (response.isSuccessful) {
                    val result = response.body()
                    handleValidationResponse(result)
                } else {
                    println("Validation API Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Validation API Exception: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    private fun handleValidationResponse(result: RequestTokenResponse?) {

        if (result != null) {
            println("Validation Succeeded: ${result.success}")
        } else {
            println("Validation Failed: Result is null.")
        }
    }

    /*
        fun createSessionId(apiKey: String, requestToken: String) {
            viewModelScope.launch {
                val result = authRepository.createSessionId(apiKey, requestToken)
                _sessionId.value = result
            }
        }

     */
}