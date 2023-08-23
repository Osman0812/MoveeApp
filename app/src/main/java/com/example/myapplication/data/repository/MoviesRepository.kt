package com.example.myapplication.data.repository

import androidx.paging.PagingData
import com.example.myapplication.data.model.genresmodel.GenresDto
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsDto
import com.example.myapplication.data.model.moviesmodel.ResultDto
import com.example.myapplication.data.model.singlemoviemodel.SingleMovieDto
import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.remote.network.pagination.createPager
import com.example.myapplication.data.remote.service.MoviesService
import com.example.myapplication.util.state.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesService: MoviesService): SafeApiRequest() {
    fun getPlayingMovies(): Flow<PagingData<ResultDto>> = createPager { page ->
        moviesService.getNowPlayingMovies(page).body()?.results ?: emptyList()
    }.flow
    fun getPopularMovies(): Flow<PagingData<ResultDto>> = createPager {page ->
        moviesService.getPopularMovies(page).body()?.results ?: emptyList()
    }.flow

    suspend fun getAllGenres(): ApiResult<GenresDto> {
        return apiRequest { moviesService.getAllGenres() }
    }

    suspend fun getSingleMovie(movieId: Int) : ApiResult<SingleMovieDto> {
        return apiRequest { moviesService.getSingleMovieInfo(movieId) }
    }

    suspend fun getMovieCredits(movieId: Int): ApiResult<MovieCreditsDto> {
        return apiRequest { moviesService.getMovieCredits(movieId) }
    }
}