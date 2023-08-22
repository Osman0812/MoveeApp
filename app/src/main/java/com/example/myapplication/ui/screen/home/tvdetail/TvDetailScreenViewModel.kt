package com.example.myapplication.ui.screen.home.tvdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.TvSeriesRepository
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreenViewModel
import com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel.TvSeriesDiretorsUiModel
import com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel.TvSeriesUiModel
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailScreenViewModel @Inject constructor(private val tvSeriesRepository: TvSeriesRepository) :
    ViewModel() {
    private val _singleTvInfoFlow =
        MutableStateFlow<DataState<TvSeriesUiModel>>(DataState.Loading)
    val singleTvInfoFlow: StateFlow<DataState<TvSeriesUiModel>> get() = _singleTvInfoFlow


    private val _tvDirectorsFlow =
        MutableStateFlow<DataState<TvSeriesDiretorsUiModel>>(DataState.Loading)
    val tvDirectorsFlow: StateFlow<DataState<TvSeriesDiretorsUiModel>> get() = _tvDirectorsFlow
    fun getSingleTvInfo(seriesId: Int) {
        viewModelScope.launch {
            when (val apiResponse = tvSeriesRepository.getSingleTv(seriesId)) {
                is ApiResult.Success -> {
                    val tvInfo = apiResponse.response.body()
                    if (tvInfo != null) {
                        _singleTvInfoFlow.value = DataState.Success(
                            TvSeriesUiModel(
                                tvTitle = tvInfo.name,
                                tvReleaseDate = tvInfo.firstAirDate,
                                tvDuration = tvInfo.episodeRunTime,
                                tvGenres = tvInfo.genres,
                                tvOverview = tvInfo.overview,
                                tvPosterPath = tvInfo.posterPath,
                                tvVoteAverage = String.format("%.1f", tvInfo.voteAverage),
                                tvNumberOfSeasons = tvInfo.numberOfSeasons
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    _singleTvInfoFlow.value =
                        DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))

                }
            }
        }
    }

    fun getTvSeriesWriters(seriesId: Int) {
        viewModelScope.launch {
            when (val directorsResponse = tvSeriesRepository.getTvSeriesCredits(seriesId)) {
                is ApiResult.Success -> {
                    val tvSeriesCredits = directorsResponse.response.body()
                    if (tvSeriesCredits != null){
                        _tvDirectorsFlow.value = DataState.Success(
                            TvSeriesDiretorsUiModel(
                                director = tvSeriesCredits.crew.filter { it.job == "Director" }
                                    .joinToString { it.name }
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
                }
            }
        }
    }
}