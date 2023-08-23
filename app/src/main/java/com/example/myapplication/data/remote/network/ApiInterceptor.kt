package com.example.myapplication.data.remote.network


import androidx.compose.ui.text.intl.Locale
import okhttp3.Interceptor
import okhttp3.Response


class ApiInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        private const val HEADER_API_KEY = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url.newBuilder()
            .addQueryParameter("language", getCurrentLanguage())
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .addHeader(HEADER_API_KEY, "Bearer $apiKey")
            .build()

        return chain.proceed(request)
    }

    private fun getCurrentLanguage(): String {
        val currentLanguage = Locale.current.language

        return if (currentLanguage == "tr") {
            "tr"
        } else {
            "en"
        }
    }
}