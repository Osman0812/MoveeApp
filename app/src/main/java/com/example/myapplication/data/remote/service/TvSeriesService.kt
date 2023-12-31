package com.example.myapplication.data.remote.service

import com.example.myapplication.data.model.genresmodel.GenresDto
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailDto
import com.example.myapplication.data.model.tvseriescreditsmodel.TvSeriesCreditsDto
import com.example.myapplication.data.model.tvseriesmodel.TvSeriesDto
import com.example.myapplication.data.remote.service.Constants.POPULAR_TV_PATH
import com.example.myapplication.data.remote.service.Constants.SINGLE_TV_PATH
import com.example.myapplication.data.remote.service.Constants.TOP_RATED_TV_SERIES
import com.example.myapplication.data.remote.service.Constants.TV_CREDITS_PATH
import com.example.myapplication.data.remote.service.Constants.TV_GENRES_PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvSeriesService {

    @GET(TOP_RATED_TV_SERIES)
    suspend fun getTopRatedTVSeries(
        @Query("page") pageNumber: Int
    ): Response<TvSeriesDto>
    @GET(TV_GENRES_PATH)
    suspend fun getTVSeriesGenres(
    ): Response<GenresDto>
    @GET(POPULAR_TV_PATH)
    suspend fun getPopularTVSeries(
        @Query("page") page: Int
    ): Response<TvSeriesDto>
    @GET(SINGLE_TV_PATH)
    suspend fun getSingleTVInfo(
        @Path("series_id") seriesId: Int
    ): Response<TvSeriesDetailDto>
    @GET(TV_CREDITS_PATH)
    suspend fun getTvSeriesCredits(
        @Path("series_id") seriesId: Int
    ): Response<TvSeriesCreditsDto>
}