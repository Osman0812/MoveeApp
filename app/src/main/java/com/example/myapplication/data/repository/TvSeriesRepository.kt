package com.example.myapplication.data.repository

import androidx.paging.PagingData
import com.example.myapplication.data.model.genresmodel.GenresModel
import com.example.myapplication.data.model.tvseriesmodel.Result
import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.remote.network.pagination.createPager
import com.example.myapplication.data.remote.service.TvSeriesService
import com.example.myapplication.util.state.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvSeriesRepository @Inject constructor(private val tvSeriesService: TvSeriesService): SafeApiRequest()  {

    fun getPopularTVSeries(): Flow<PagingData<Result>> = createPager { page ->
        tvSeriesService.getPopularTVSeries(page).body()!!.results
    }.flow

    fun getTopRatedTVSeries(): Flow<PagingData<Result>> = createPager { page->
        tvSeriesService.getTopRatedTVSeries(page).body()!!.results
    }.flow

    suspend fun getAllGenres(): ApiResult<GenresModel> {
        return apiRequest { tvSeriesService.getTVSeriesGenres() }
    }
}