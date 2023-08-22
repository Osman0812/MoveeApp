package com.example.myapplication.data.di


import com.example.myapplication.BuildConfig
import com.example.myapplication.data.remote.network.ApiInterceptor
import com.example.myapplication.data.remote.service.AuthService
import com.example.myapplication.data.remote.service.MoviesService
import com.example.myapplication.data.remote.service.TvSeriesService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiKeyInterceptor(): ApiInterceptor {
        return ApiInterceptor(BuildConfig.TMDB_API_KEY)
    }
    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
    @Provides
    fun provideOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @Provides
    @Singleton
    fun provideTvSeriesService(retrofit: Retrofit): TvSeriesService =
        retrofit.create(TvSeriesService::class.java)
}