package com.example.myapplication.data.remote.network

import com.example.myapplication.util.state.ApiResult
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): ApiResult<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null) {
                    ApiResult.Success(response)
                } else {
                    ApiResult.Error("Login Error!")
                }
            } else {
                val error = response.errorBody()?.string()
                val message = StringBuilder()
                error?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (e: JSONException) {
                        return ApiResult.Error("Login Error!")
                    }
                    message.append("\n")
                }
                message.append("Error code: ${response.code()}")
                return if (response.code() >= 500) {
                    ApiResult.Error(message.toString())
                } else {
                    ApiResult.Error("Login Error!")
                }
            }
        } catch (e: java.lang.Exception) {
            println(e.message.toString())
            return ApiResult.Error("Network Error!")
        }
    }
}