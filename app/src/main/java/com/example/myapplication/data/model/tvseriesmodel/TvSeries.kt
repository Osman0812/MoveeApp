package com.example.myapplication.data.model.tvseriesmodel

import com.google.gson.annotations.SerializedName
data class TvSeriesDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)