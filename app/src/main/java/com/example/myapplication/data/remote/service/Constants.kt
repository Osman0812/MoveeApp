package com.example.myapplication.data.remote.service

object Constants {
    const val NEW_REQUEST_TOKEN_PATH = "/3/authentication/token/new"
    const val LOGIN_VALIDATE_PATH = "/3/authentication/token/validate_with_login"
    const val GET_SESSION_ID_PATH = "/3/authentication/session/new"

    const val NOW_PLAYING_PATH = "/3/movie/now_playing"
    const val POPULAR_PATH = "/3/movie/popular"
    const val ALL_GENRES_PATH = "/3/genre/movie/list"

    const val SINGLE_MOVIE_PATH = "/3/movie/{movie_id}"
    const val MOVIE_CREDITS_PATH = "/3/movie/{movie_id}/credits"

    const val POPULAR_TV_PATH = "/3/tv/popular"
    const val TV_GENRES_PATH = "/3/genre/tv/list"
    const val TOP_RATED_TV_SERIES = "3/tv/top_rated"
    const val SINGLE_TV_PATH = "3/tv/{series_id}"
    const val TV_CREDITS_PATH = "/3/tv/{series_id}/credits"

    const val SEARCH_MULTI_PATH = "/3/search/multi"

    const val ACTOR_DETAIL = "/3/person/{person_id}"
    const val ACTOR_MOVIES_CREDITS = "/3/person/{person_id}/movie_credits"
    const val ACTOR_TV_SERIES_CREDITS = "/3/person/{person_id}/tv_credits"
}
