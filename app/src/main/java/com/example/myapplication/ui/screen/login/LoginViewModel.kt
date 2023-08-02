package com.example.myapplication.ui.screen.login

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.ValidationCreateRequest
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.util.extension.ApiResult
import com.example.myapplication.util.extension.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    var requestToken = ""
    private val _sessionId = MutableStateFlow<ResultOf<Unit>>(ResultOf.Initial)
    val sessionId: StateFlow<ResultOf<Unit>> = _sessionId
    fun createRequestToken(): Deferred<Unit> = viewModelScope.async {
        when (val result = authRepository.createRequestToken()) {
            is ApiResult.Success -> {
                val token = result.response.body()
                requestToken = token?.requestToken.toString()
            }
            is ApiResult.Error -> {
                _sessionId.value = ResultOf.Error(Exception("Request Token Failed!"))
            }
        }
    }
    fun performValidation(user: ValidationRequest) {
        viewModelScope.launch {
            val job = createRequestToken()
            job.await()
            when (val apiResponse = authRepository.validateRequestToken(user, requestToken)) {
                is ApiResult.Success -> {
                    val result = apiResponse.response.body()
                    println("Validation Succeeded: ${result?.success}")
                    getSessionId()
                }
                is ApiResult.Error -> {
                    _sessionId.value = ResultOf.Error(Exception("Validation Failed!"))
                }
            }
        }
    }
    private fun getSessionId() {
        viewModelScope.launch {
            val token = ValidationCreateRequest(requestToken)
            when (val apiResponse = authRepository.createSessionId(token)) {
                is ApiResult.Success -> {
                    val result = apiResponse.response
                    _sessionId.value = ResultOf.Success(result.body()!!.sessionId)
                }
                is ApiResult.Error -> {
                    _sessionId.value = ResultOf.Error(Exception("Hata"))
                }
            }
        }
    }
}