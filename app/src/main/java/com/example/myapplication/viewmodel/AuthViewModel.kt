package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.remote.model.ValidationCreateRequest
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    var requestToken = ""
    private val _sessionId = MutableLiveData<String>()
    val sessionId: LiveData<String> = _sessionId
    fun createRequestToken(): Deferred<Unit> = viewModelScope.async {
        val result = authRepository.createRequestToken()
        requestToken = result.body()!!.request_token
    }
    fun performValidation(user: ValidationRequest){
        viewModelScope.launch {
            val job = createRequestToken()
            job.await()
            try {
                val response = authRepository.validateRequestToken(user, requestToken)
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                         println("Validation Succeeded: ${result.success}")
                         val task = getSessionId()
                         task.await()
                    }
                }
            } catch (e: Exception) {
                println("Validation API Exception: ${e.message}")
                e.printStackTrace()
            }
        }
    }
    fun getSessionId(): Deferred<Unit> = viewModelScope.async  {
        viewModelScope.launch {
            try {
                val token = ValidationCreateRequest(requestToken)
                val response = authRepository.createSessionId(token)
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _sessionId.value = result.session_id
                    }
                }
            } catch (e: Exception) {
                println("Session ID API Exception: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}