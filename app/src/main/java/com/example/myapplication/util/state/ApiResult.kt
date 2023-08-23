package com.example.myapplication.util.state

import retrofit2.Response

sealed class ApiResult<out T> {
    data class Success<out T>(val response: Response<out T>) : ApiResult<T>()
    data class Error(val exception: String) : ApiResult<Nothing>()
}