package com.example.myapplication.ui.screen.home.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.MoviesRepository
import com.example.myapplication.ui.screen.home.moviedetail.movieuimodel.MovieUiModel
import com.example.myapplication.ui.screen.home.moviedetail.movieuimodel.MoviesCreditsUiModel
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
        MutableStateFlow<DataState<MovieUiModel>>(DataState.Loading)
    val singleMovieInfoFlow: StateFlow<DataState<MovieUiModel>> get() = _singleMovieInfoFlow

    private val _movieCreditsFlow =
        MutableStateFlow<DataState<MoviesCreditsUiModel>>(DataState.Loading)
    val movieCreditsFlow: StateFlow<DataState<MoviesCreditsUiModel>> get() = _movieCreditsFlow
    fun getSingleMovieInfo(movieId: Int) {
        viewModelScope.launch {
            when (val apiResponse = moviesRepository.getSingleMovie(movieId)) {
                is ApiResult.Success -> {
                    val movieInfo = apiResponse.response.body()
                    if (movieInfo != null) {
                        _singleMovieInfoFlow.value = DataState.Success(
                            MovieUiModel(
                                movieTitle = movieInfo.title,
                                movieReleaseDate = movieInfo.releaseDate,
                                movieDuration = movieInfo.runtime,
                                movieGenres = movieInfo.genres,
                                voteAverage = String.format("%.1f", movieInfo.voteAverage),
                                posterPath = movieInfo.posterPath,
                                overview = movieInfo.overview
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    _singleMovieInfoFlow.value =
                        DataState.Error(Exception(ErrorMessages.GENERIC_ERROR))

                }
            }
        }
    }

    fun getMovieCredit(movieId: Int) {
        viewModelScope.launch {
            when (val apiResponse = moviesRepository.getMovieCredits(movieId)) {
                is ApiResult.Success -> {
                    val movieCredits = apiResponse.response.body()
                    if (movieCredits != null) {
                        _movieCreditsFlow.value = DataState.Success(
                            MoviesCreditsUiModel(
                                director = movieCredits.crew.filter { it.job == "Director" }
                                    .joinToString { it.name },
                                stars = movieCredits.cast.filter { it.order == 0 || it.order == 1 }
                                    .joinToString { it.originalName },
                                writer = movieCredits.crew.filter { it.job == "Author" || it.job == "Writer" }
                                    .joinToString { it.name }

                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    DataState.Error(Exception(ErrorMessages.GENERIC_ERROR))
                }
            }
        }
    }

    object ErrorMessages {
        const val GENERIC_ERROR = "Data cannot be fetched!"
    }
}