package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.genresmodel.GenresDto
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsDto
import com.example.myapplication.data.model.moviesmodel.MoviesDto
import com.example.myapplication.data.model.singlemoviemodel.SingleMovieDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET(Constants.NOW_PLAYING_PATH)
    suspend fun getNowPlayingMovies(
        @Query("page") pageNumber: Int
    ): Response<MoviesDto>

    @GET(Constants.POPULAR_PATH)
    suspend fun getPopularMovies(
        @Query("page") pageNumber: Int
    ): Response<MoviesDto>

    @GET(Constants.ALL_GENRES_PATH)
    suspend fun getAllGenres(): Response<GenresDto>

    @GET(Constants.SINGLE_MOVIE_PATH)
    suspend fun getSingleMovieInfo(
        @Path("movie_id") movieId: Int
    ): Response<SingleMovieDto>

    @GET(Constants.MOVIE_CREDITS_PATH)
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Response<MovieCreditsDto>
}