package com.example.myapplication.data.repository

import androidx.paging.PagingData
import com.example.myapplication.data.model.genresmodel.GenresDto
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsDto
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailDto
import com.example.myapplication.data.model.tvseriescreditsmodel.TvSeriesCreditsDto
import com.example.myapplication.data.model.tvseriesmodel.ResultDto
import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.remote.network.pagination.createPager
import com.example.myapplication.data.remote.service.TvSeriesService
import com.example.myapplication.util.state.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvSeriesRepository @Inject constructor(private val tvSeriesService: TvSeriesService): SafeApiRequest()  {

    fun getPopularTVSeries(): Flow<PagingData<ResultDto>> = createPager { page ->
        tvSeriesService.getPopularTVSeries(page).body()?.results ?: emptyList()
    }.flow

    fun getTopRatedTVSeries(): Flow<PagingData<ResultDto>> = createPager { page->
        tvSeriesService.getTopRatedTVSeries(page).body()?.results ?: emptyList()
    }.flow

    suspend fun getTvSeriesAllGenres(): ApiResult<GenresDto> {
        return apiRequest { tvSeriesService.getTVSeriesGenres() }
    }
    suspend fun getSingleTv(seriesId: Int) : ApiResult<TvSeriesDetailDto> {
        return apiRequest { tvSeriesService.getSingleTVInfo(seriesId) }
    }
    suspend fun getTvSeriesCredits(seriesId: Int): ApiResult<TvSeriesCreditsDto> {
        return apiRequest { tvSeriesService.getTvSeriesCredits(seriesId) }
    }
}