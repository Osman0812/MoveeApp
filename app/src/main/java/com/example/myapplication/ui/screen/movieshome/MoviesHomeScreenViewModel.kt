package com.example.myapplication.ui.screen.movieshome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.data.model.genresmodel.Genre
import com.example.myapplication.data.model.moviesmodel.Result
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
    val moviesPlayNowFlow: Flow<PagingData<Result>> = moviesRepository.getPlayingMovies().cachedIn(viewModelScope)
    val moviesPopularFlow: Flow<PagingData<Result>> = moviesRepository.getPopularMovies().cachedIn(viewModelScope)

    private val _allGenresFlow = MutableStateFlow<DataState<List<Genre>>>(DataState.Loading)
    val allGenresFlow: StateFlow<DataState<List<Genre>>> get() = _allGenresFlow

    init{
        getAllGenres()
    }

    private fun getAllGenres() {
        viewModelScope.launch {
            val apiResponse = moviesRepository.getAllGenres()
            when(apiResponse) {
                is ApiResult.Success -> {
                    val genres = apiResponse.response.body()?.genres
                    _allGenresFlow.value = DataState.Success(genres ?: emptyList())
                }
                is ApiResult.Error -> {
                    _allGenresFlow.value = DataState.Error(Exception("Genres cannot be fetched!"))
                }
            }
        }
    }


}