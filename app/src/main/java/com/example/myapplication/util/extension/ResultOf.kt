package com.example.myapplication.util.extension

sealed class ResultOf<out T> {
    object Initial : ResultOf<Nothing>()
    object Loading : ResultOf<Nothing>()
    data class Success<out T>(val sessionId: String) : ResultOf<T>()
    data class Error(val exception: Exception) : ResultOf<Nothing>()
}