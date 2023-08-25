package com.example.myapplication.ui.screen.searchscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsDto
import com.example.myapplication.data.model.tvseriescreditsmodel.TvSeriesCreditsDto
import com.example.myapplication.data.repository.MoviesRepository
import com.example.myapplication.data.repository.SearchRepository
import com.example.myapplication.data.repository.TvSeriesRepository
import com.example.myapplication.ui.screen.searchscreen.searchscreenuimodel.MediaTypeUiModel
import com.example.myapplication.ui.screen.searchscreen.searchscreenuimodel.SearchUiModel
import com.example.myapplication.util.Constants
import com.example.myapplication.util.DataTransformer
import com.example.myapplication.util.state.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val tvRepository: TvSeriesRepository,
    private val moviesRepository: MoviesRepository,
) : ViewModel() {
    var searchState = mutableStateOf("")
    private val _searchQuery = MutableStateFlow("")
    private val searchQuery: Flow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResultFlow: Flow<PagingData<SearchUiModel>> = searchQuery.flatMapLatest { query ->
        searchRepository.searchItems(query).map { pagingData ->
            pagingData.filter { item ->
                item.mediaType in listOf(Constants.MOVIE, Constants.TV)
            }.map { searchItem ->
                DataTransformer.transformData(searchItem) { item ->
                    val mediaTypeUiModel = getMediaTypeUiModel(
                        item.mediaType
                    )
                    SearchUiModel(
                        itemName = mediaTypeUiModel.getItemName.invoke(item.name, item.title) ?: "",
                        itemImage = mediaTypeUiModel.getItemImage.invoke(
                            item.profilePath,
                            item.posterPath
                        ) ?: "",
                        itemDescription = getDescription(mediaTypeUiModel, item.id),
                        itemType = item.mediaType,
                        itemId = item.id,
                    )
                }
            }
        }
    }.cachedIn(viewModelScope)

    fun handleSearchStateChange(searchValue: String) {
        if (searchValue.isEmpty()) {
            setQuery()
            return
        }

        if (searchValue.length < 4) {
            return
        }
        setQuery()
    }

    private fun setQuery() {
        _searchQuery.value = searchState.value
    }

    private suspend fun getDescription(description: MediaTypeUiModel, id: Int): String {
        return when (description.type) {
            Constants.TV -> getTvCast(id)
            Constants.MOVIE -> getMovieCast(id)
            else -> ErrorMessages.DESCRIPTION_ERROR
        }
    }

    private suspend fun getTVCast(
        id: Int, fetchCredits: suspend (Int) -> ApiResult<TvSeriesCreditsDto>
    ): String {
        return when (val apiResponse = fetchCredits(id)) {
            is ApiResult.Success -> {
                val cast = apiResponse.response.body()?.cast
                val casts =
                    cast?.filter { it.order == 0 || it.order == 1 }?.joinToString { it.name } ?: ""
                casts
            }

            is ApiResult.Error -> ErrorMessages.TV_ERROR
        }
    }

    private suspend fun getMovieCast(
        id: Int, fetchCredits: suspend (Int) -> ApiResult<MovieCreditsDto>
    ): String {
        return when (val apiResponse = fetchCredits(id)) {
            is ApiResult.Success -> {
                val cast = apiResponse.response.body()?.cast
                val casts =
                    cast?.filter { it.order == 0 || it.order == 1 }?.joinToString { it.name } ?: ""
                casts
            }

            is ApiResult.Error -> ErrorMessages.MOVIE_ERROR
        }
    }

    private suspend fun getMovieCast(seriesId: Int): String {
        return getMovieCast(seriesId, moviesRepository::getMovieCredits)
    }

    private suspend fun getTvCast(movieId: Int): String {
        return getTVCast(movieId, tvRepository::getTvSeriesCredits)
    }

    private fun getMediaTypeUiModel(type: String): MediaTypeUiModel {
        return when (type) {
            Constants.MOVIE -> MediaTypeUiModel.Movie
            Constants.TV -> MediaTypeUiModel.TvSeries
            else -> throw IllegalArgumentException("Unknown media type: $type")
        }
    }

    object ErrorMessages {
        const val TV_ERROR = "Failed to get TvCast!"
        const val MOVIE_ERROR = "Failed to get MovieCast!"
        const val DESCRIPTION_ERROR = "Failed to get Description!"
    }
}