package com.example.myapplication.data.repository

import androidx.paging.PagingData
import com.example.myapplication.data.model.genresmodel.GenresModel
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsModel
import com.example.myapplication.data.model.moviesmodel.Result
import com.example.myapplication.data.model.singlemoviemodel.SingleMovieModel
import com.example.myapplication.data.remote.network.SafeApiRequest
import com.example.myapplication.data.remote.network.pagination.createPager
import com.example.myapplication.data.remote.service.MoviesService
import com.example.myapplication.util.state.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesService: MoviesService): SafeApiRequest() {
    fun getPlayingMovies(): Flow<PagingData<Result>> = createPager { page ->
        moviesService.getNowPlayingMovies(page).body()!!.results
    }.flow

    fun getPopularMovies(): Flow<PagingData<Result>> = createPager {page ->
        moviesService.getPopularMovies(page).body()!!.results
    }.flow

    suspend fun getAllGenres(): ApiResult<GenresModel> {
        return apiRequest { moviesService.getAllGenres() }
    }

    suspend fun getSingleMovie(movieId: Int) : ApiResult<SingleMovieModel> {
        return apiRequest { moviesService.getSingleMovieInfo(movieId) }
    }

    suspend fun getMovieCredits(movieId: Int): ApiResult<MovieCreditsModel> {
        return apiRequest { moviesService.getMovieCredits(movieId) }
    }
}