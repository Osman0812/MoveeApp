package com.example.myapplication.ui

import com.example.myapplication.R

sealed class BottomBarScreen(
    val route: String,
    val unselectedIcon: Int,
    val selectedIcon: Int
) {
    object Movies : BottomBarScreen(
        route = "MOVIES_HOME_SCREEN",
        unselectedIcon = R.drawable.ic_bottombar_movie,
        selectedIcon = R.drawable.ic_bottombar_movie_selected
    )

    object TvSeries : BottomBarScreen(
        route = "TV_SERIES_SCREEN",
        unselectedIcon = R.drawable.ic_bottombar_tv_series,
        selectedIcon = R.drawable.ic_bottombar_tv_series_selected
    )
    object Search : BottomBarScreen(
        route = "SEARCH_SCREEN",
        unselectedIcon = R.drawable.icons_tabbar_search,
        selectedIcon = R.drawable.icons_tabbar_search_selected
    )
}