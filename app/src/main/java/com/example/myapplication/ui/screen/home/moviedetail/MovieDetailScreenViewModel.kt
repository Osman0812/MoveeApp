package com.example.myapplication.ui.screen.home.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsModel
import com.example.myapplication.data.model.singlemoviemodel.SingleMovieModel
import com.example.myapplication.data.repository.MoviesRepository
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _singleMovieInfoFlow =
        MutableStateFlow<DataState<SingleMovieModel>>(DataState.Loading)
    val singleMovieInfoFlow: StateFlow<DataState<SingleMovieModel>> get() = _singleMovieInfoFlow

    private val _movieCreditsFlow =
        MutableStateFlow<DataState<MovieCreditsModel>>(DataState.Loading)
    val movieCreditsFlow: StateFlow<DataState<MovieCreditsModel>> get() = _movieCreditsFlow

    fun getSingleMovieInfo(movieId: Int) {
        viewModelScope.launch {
            val apiResponse = moviesRepository.getSingleMovie(movieId)

            when (apiResponse) {
                is ApiResult.Success -> {
                    val movieInfo = apiResponse.response.body()
                    _singleMovieInfoFlow.value = DataState.Success(movieInfo!!)
                }

                is ApiResult.Error -> {
                    _singleMovieInfoFlow.value =
                        DataState.Error(Exception("Data cannot be fetched!"))
                }
            }

        }
    }

    fun getMovieCredit(movieId: Int) {
        viewModelScope.launch {

            when (val apiResponse = moviesRepository.getMovieCredits(movieId)) {
                is ApiResult.Success -> {
                    val movieCredits = apiResponse.response.body()
                    _movieCreditsFlow.value = DataState.Success(movieCredits!!)
                }

                is ApiResult.Error -> {
                    _movieCreditsFlow.value = DataState.Error(Exception("Data cannot be fetched!"))
                }
            }
        }
    }


}