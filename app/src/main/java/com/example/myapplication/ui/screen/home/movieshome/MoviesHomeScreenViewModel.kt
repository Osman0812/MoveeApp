package com.example.myapplication.ui.screen.home.movieshome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.model.genresmodel.GenreDto
import com.example.myapplication.data.model.moviesmodel.ResultDto
import com.example.myapplication.data.repository.MoviesRepository
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MoviesHomeScreenViewModel @Inject constructor(private val moviesRepository: MoviesRepository) : ViewModel() {
    val moviesPlayNowFlow: Flow<PagingData<ResultDto>> = moviesRepository.getPlayingMovies().cachedIn(viewModelScope)

    val moviesPopularFlow: Flow<PagingData<ResultDto>> = moviesRepository.getPopularMovies().cachedIn(viewModelScope)

    private val _allGenresFlow = MutableStateFlow<DataState<List<GenreDto>>>(DataState.Loading)
    val allGenresFlow: StateFlow<DataState<List<GenreDto>>> get() = _allGenresFlow

    init{
        getAllGenres()
    }

    private fun getAllGenres() {
        viewModelScope.launch {
            when(val apiResponse = moviesRepository.getAllGenres()) {
                is ApiResult.Success -> {
                    val genres = apiResponse.response.body()?.genres
                    _allGenresFlow.value = DataState.Success(genres ?: emptyList())
                    println(genres)
                }
                is ApiResult.Error -> {
                    _allGenresFlow.value = DataState.Error(Exception("Genres cannot be fetched!"))
                }
            }
        }
    }
}