package com.example.myapplication.data.remote.service


import com.example.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
            .build()
        return chain.proceed(newRequest)
    }
}