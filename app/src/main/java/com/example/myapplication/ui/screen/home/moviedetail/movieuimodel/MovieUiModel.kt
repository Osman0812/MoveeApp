package com.example.myapplication.ui.screen.home.moviedetail.movieuimodel

import com.example.myapplication.data.model.singlemoviemodel.GenreDto

data class MovieUiModel(
    var posterPath: String? = null,
    var voteAverage: String? = null,
    var movieTitle: String?=null ,
    var movieGenres: List<GenreDto>? = null,
    var movieDuration: Int? = null,
    var movieReleaseDate: String? = null,
    var overview: String? = null
)
