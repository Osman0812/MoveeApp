package com.example.myapplication.ui.screen.home.actordetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.ActorRepository
import com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel.ActorDetailsUIModel
import com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel.ActorMovieCreditUI
import com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel.ActorTVCreditUi
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreenViewModel
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailScreenViewModel @Inject constructor(private val actorRepository: ActorRepository) :
    ViewModel() {
    private val _singleActorInfoFlow =
        MutableStateFlow<DataState<ActorDetailsUIModel>>(DataState.Loading)
    val singleActorInfoFlow: StateFlow<DataState<ActorDetailsUIModel>> get() = _singleActorInfoFlow

    private val _movieCreditsFlow =
        MutableStateFlow<DataState<ActorMovieCreditUI>>(DataState.Loading)
    val movieCreditsFlow: StateFlow<DataState<ActorMovieCreditUI>> get() = _movieCreditsFlow
    private val _tvCreditsFlow =
        MutableStateFlow<DataState<ActorTVCreditUi>>(DataState.Loading)
    val tvCreditsFlow: StateFlow<DataState<ActorTVCreditUi>> get() = _tvCreditsFlow
    fun getSingleActorInfo(personId: Int) {
        viewModelScope.launch {
            when (val apiResponse = actorRepository.getActorDetails(personId)) {
                is ApiResult.Success -> {
                    val actorInfo = apiResponse.response.body()
                    if (actorInfo != null) {
                        _singleActorInfoFlow.value = DataState.Success(
                            ActorDetailsUIModel(
                                profilePath = actorInfo.profilePath,
                                placeOfBirth = actorInfo.placeOfBirth,
                                birthday = actorInfo.birthday,
                                biography = actorInfo.biography,
                                name = actorInfo.name
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    _singleActorInfoFlow.value =
                        DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))

                }
            }
        }
    }

    fun getMovieCredits(actorId: Int) {
        viewModelScope.launch {
            when (val apiResponse = actorRepository.getActorMovieCredits(actorId)) {
                is ApiResult.Success -> {
                    val movieCredits = apiResponse.response.body()
                    if (movieCredits != null) {
                        _movieCreditsFlow.value = DataState.Success(
                            ActorMovieCreditUI(
                                cast = movieCredits.cast,
                                crew = movieCredits.crew,
                                id = movieCredits.id
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    _movieCreditsFlow.value =
                        DataState.Error(java.lang.Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
                }
            }
        }
    }

    fun getTvSeriesCredits(actorId: Int) {
        viewModelScope.launch {
            when (val apiResponse = actorRepository.getActorTvSeriesCredits(actorId)) {
                is ApiResult.Success -> {
                    val tvCredits = apiResponse.response.body()
                    if (tvCredits != null) {
                        _tvCreditsFlow.value = DataState.Success(
                            ActorTVCreditUi(
                                cast = tvCredits.cast,
                                crew = tvCredits.crew,
                                id = tvCredits.id
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    _tvCreditsFlow.value =
                        DataState.Error(java.lang.Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
                }
            }
        }
    }
}