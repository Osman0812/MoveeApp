package com.example.myapplication.ui.screen.home.tvseries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.model.genresmodel.Genre
import com.example.myapplication.data.model.tvseriesmodel.Result
import com.example.myapplication.data.repository.TvSeriesRepository
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvSeriesViewModel @Inject constructor(private val tvSeriesRepository: TvSeriesRepository): ViewModel() {

    val tvSeriesTopRatedFlow: Flow<PagingData<Result>> = tvSeriesRepository.getTopRatedTVSeries().cachedIn(viewModelScope)
    val tvSeriesPopularFlow: Flow<PagingData<Result>> = tvSeriesRepository.getPopularTVSeries().cachedIn(viewModelScope)
    private val _allGenresFlow = MutableStateFlow<DataState<List<Genre>>>(DataState.Loading)
    val tvSeriesGenresFlow: StateFlow<DataState<List<Genre>>> get() = _allGenresFlow

    init {
        getAllGenres()
    }

    private fun getAllGenres() {
        viewModelScope.launch {
            val apiResponse = tvSeriesRepository.getAllGenres()

            when (apiResponse) {
                is ApiResult.Success -> {
                    val genres = apiResponse.response.body()?.genres
                    _allGenresFlow.value = DataState.Success(genres ?: emptyList())
                }

                is ApiResult.Error -> {
                    _allGenresFlow.value = DataState.Error(Exception("Genres Error!"))
                }
            }
        }
    }



}