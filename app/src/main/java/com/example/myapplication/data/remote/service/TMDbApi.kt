package com.example.myapplication.data.remote.service



import com.example.myapplication.data.remote.model.RequestTokenResponse
import com.example.myapplication.data.remote.model.ValidationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface TMDbApi {
    //api key = f2f4bcd525ac9cb9ef55fb2697cfc39b

    @GET("/3/authentication/token/new")
    suspend fun createRequestToken(
        @Header("Authorization") apiKey: String
    ): Response<RequestTokenResponse>


    @POST("/3/authentication/token/validate_with_login")
    suspend fun validateRequestToken(
        @Header("Authorization") apiKey: String,
        @Body user: ValidationRequest
    ): Response<RequestTokenResponse>
/*
    @POST("authentication/session/new")
    suspend fun createSessionId(
        @Query("api_key") apiKey: String,
        @Query("request_token") requestToken: String
    ): SessionResponse

    companion object {
        fun create(apiKey: String): TMDbApi {
            val client = OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor(apiKey))
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(TMDbApi::class.java)
        }
    }

     */
}


