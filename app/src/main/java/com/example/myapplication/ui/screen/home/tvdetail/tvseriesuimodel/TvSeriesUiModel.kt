package com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel

import com.example.myapplication.data.model.genresmodel.GenreDto


data class TvSeriesUiModel(
    var tvPosterPath: String? = null,
    var tvVoteAverage: String? = null,
    var tvTitle: String? = null,
    var tvGenres: List<GenreDto>? = null,
    var tvDuration: List<Int>? = null,
    var tvReleaseDate: String? = null,
    var tvOverview: String? = null,
    val tvNumberOfSeasons: Int? = null,
)