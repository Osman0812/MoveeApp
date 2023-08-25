package com.example.myapplication.data.repository

import androidx.paging.PagingData
import com.example.myapplication.data.model.searchmodel.Result
import com.example.myapplication.data.remote.network.pagination.createPager
import com.example.myapplication.data.remote.service.SearchService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchService: SearchService) {
    fun searchItems(query: String): Flow<PagingData<Result>> = createPager { page ->
        searchService.searchMulti(page, query).body()!!.results
    }.flow
}