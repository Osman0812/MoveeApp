package com.example.myapplication.ui.screen.home.tvdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsModel
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailModel
import com.example.myapplication.data.model.tvseriescreditsmodel.TvSeriesCreditsModel
import com.example.myapplication.data.repository.TvSeriesRepository
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailScreenViewModel @Inject constructor(private val tvSeriesRepository: TvSeriesRepository) : ViewModel(){
    private val _singleTvInfoFlow =
        MutableStateFlow<DataState<TvSeriesDetailModel>>(DataState.Loading)
    val singleTvInfoFlow: StateFlow<DataState<TvSeriesDetailModel>> get() = _singleTvInfoFlow
    private val _tvCreditsFlow =
        MutableStateFlow<DataState<TvSeriesCreditsModel>>(DataState.Loading)
    val tvCreditsFlow: StateFlow<DataState<TvSeriesCreditsModel>> get() = _tvCreditsFlow
    fun getSingleTvInfo(seriesId: Int) {
        viewModelScope.launch {
            when (val apiResponse = tvSeriesRepository.getSingleTv(seriesId)) {

                is ApiResult.Success -> {
                    val movieInfo = apiResponse.response.body()
                    _singleTvInfoFlow.value = DataState.Success(movieInfo!!)
                }

                is ApiResult.Error -> {
                    _singleTvInfoFlow.value =
                        DataState.Error(Exception("Data cannot be fetched!"))
                }
            }
        }
    }
    fun getTvSeriesCredit(seriesId: Int) {
        viewModelScope.launch {

            when (val apiResponse = tvSeriesRepository.getTvSeriesCredits(seriesId)) {
                is ApiResult.Success -> {
                    val tvCredits = apiResponse.response.body()
                    _tvCreditsFlow.value = DataState.Success(tvCredits!!)
                }

                is ApiResult.Error -> {
                    _tvCreditsFlow.value = DataState.Error(Exception("Data cannot be fetched!"))
                }
            }
        }
    }

    fun createProfileImageUrl(profilePath: String?): String {
        val baseUrl = Constants.IMAGE_URL
        return if (!profilePath.isNullOrBlank()) {
            "$baseUrl$profilePath"
        } else {
            "${R.drawable.movies_dummy}"
        }
    }
}