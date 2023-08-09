package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.genresmodel.GenresModel
import com.example.myapplication.data.model.tvseriesmodel.TvSeriesModel
import com.example.myapplication.data.remote.service.Constants.POPULAR_TV_PATH
import com.example.myapplication.data.remote.service.Constants.TOP_RATED_TV_SERIES
import com.example.myapplication.data.remote.service.Constants.TV_GENRES_PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeriesService {

    @GET(TOP_RATED_TV_SERIES)
    suspend fun getTopRatedTVSeries(
        @Query("page") pageNumber: Int
    ): Response<TvSeriesModel>

    @GET(TV_GENRES_PATH)
    suspend fun getTVSeriesGenres(
    ): Response<GenresModel>

    @GET(POPULAR_TV_PATH)
    suspend fun getPopularTVSeries(
        @Query("page") page: Int
    ): Response<TvSeriesModel>
}