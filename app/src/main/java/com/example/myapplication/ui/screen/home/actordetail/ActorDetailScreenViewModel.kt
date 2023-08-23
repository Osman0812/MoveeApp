package com.example.myapplication.ui.screen.home.actordetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.actormodel.ActorDetailsModel
import com.example.myapplication.data.model.moviecreditsmodel.CastDto
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsDto
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailDto
import com.example.myapplication.data.repository.ActorRepository
import com.example.myapplication.data.repository.TvSeriesRepository
import com.example.myapplication.util.state.ApiResult
import com.example.myapplication.util.state.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailScreenViewModel @Inject constructor(private val actorRepository: ActorRepository) : ViewModel(){
    private val _singleActorInfoFlow =
        MutableStateFlow<DataState<ActorDetailsModel>>(DataState.Loading)
    val singleActorInfoFlow: StateFlow<DataState<ActorDetailsModel>> get() = _singleActorInfoFlow
    fun getSingleActorInfo(personId: Int) {
        viewModelScope.launch {
            when (val apiResponse = actorRepository.getActorDetails(personId)) {
                is ApiResult.Success -> {
                    val actorInfo = apiResponse.response.body()
                    _singleActorInfoFlow.value = DataState.Success(actorInfo!!)
                }
                is ApiResult.Error -> {
                    _singleActorInfoFlow.value =
                        DataState.Error(Exception("Data cannot be fetched!"))
                }
            }
        }
    }
}