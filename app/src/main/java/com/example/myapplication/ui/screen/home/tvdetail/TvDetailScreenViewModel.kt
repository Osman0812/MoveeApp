package com.example.myapplication.ui.screen.home.tvdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.repository.TvSeriesRepository
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreenViewModel
import com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel.TvSeriesUICredits
import com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel.TvSeriesUiModel
import com.example.myapplication.util.Constants
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
    private val _tvCreditsFlow =
        MutableStateFlow<DataState<TvSeriesUICredits>>(DataState.Loading)
    val tvCreditsFlow: StateFlow<DataState<TvSeriesUICredits>> get() = _tvCreditsFlow

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
                                tvNumberOfSeasons = tvInfo.numberOfSeasons,
                                createBy = tvInfo.createdBy
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

    fun getTvSeriesCredit(seriesId: Int) {
        viewModelScope.launch {
            when (val apiResponse = tvSeriesRepository.getTvSeriesCredits(seriesId)) {
                is ApiResult.Success -> {
                    val tvCredits = apiResponse.response.body()
                    if (tvCredits != null){
                        _tvCreditsFlow.value = DataState.Success(
                            TvSeriesUICredits(
                                cast = tvCredits.cast,
                            )
                        )
                    }
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
