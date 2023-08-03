package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.genresmodel.GenresModel
import com.example.myapplication.data.model.moviesmodel.MoviesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET(Constants.NOW_PLAYING_PATH)
    suspend fun getNowPlayingMovies(
        @Query("page") pageNumber: Int
    ): Response<MoviesModel>

    @GET(Constants.POPULAR_PATH)
    suspend fun getPopularMovies(
        @Query("page") pageNumber: Int
    ): Response<MoviesModel>

    @GET(Constants.ALL_GENRES_PATH)
    suspend fun getAllGenres(): Response<GenresModel>
}