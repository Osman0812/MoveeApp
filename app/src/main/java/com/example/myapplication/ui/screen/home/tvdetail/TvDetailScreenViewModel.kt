package com.example.myapplication.ui.screen.home.tvdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailModel
import com.example.myapplication.data.repository.TvSeriesRepository
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
}