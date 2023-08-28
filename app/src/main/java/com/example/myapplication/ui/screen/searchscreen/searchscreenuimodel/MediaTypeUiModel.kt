package com.example.myapplication.ui.screen.searchscreen.searchscreenuimodel

import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.graphs.ActorScreens
import com.example.myapplication.graphs.MoviesScreens
import com.example.myapplication.graphs.TvSeriesDetailScreens
import com.example.myapplication.util.Constants

sealed class MediaTypeUiModel(
    val iconResId: Int,
    val type: String,
    val stringResourceId: Int,
    val getItemName: (String?, String?) -> String?,
    val getItemImage: (String?, String?) -> String?,
    private val navigateAction: (Int, NavController) -> Unit
) {

    fun navigate(itemId: Int, navController: NavController) = navigateAction(itemId, navController)

    object Movie : MediaTypeUiModel(
        iconResId = R.drawable.ic_bottombar_movie,
        type = Constants.MOVIE,
        stringResourceId = R.string.search_screen_movie,
        getItemName = { _, title -> title },
        getItemImage = { _, posterPath -> posterPath ?: ""},
        navigateAction = { itemId, navController ->
            navController.navigate("${MoviesScreens.MovieDetailScreen.route}/$itemId")
        }
    )

    object TvSeries : MediaTypeUiModel(
        iconResId = R.drawable.ic_bottombar_tv_series,
        type = Constants.TV,
        stringResourceId = R.string.search_screen_tv,
        getItemName = { name, _ -> name },
        getItemImage = { _, posterPath -> posterPath },
        navigateAction = { itemId, navController ->
            navController.navigate("${TvSeriesDetailScreens.TvSeriesDetailScreen.route}/$itemId")
        }
    )

    object Actor : MediaTypeUiModel(
        iconResId = R.drawable.ic_actor_img,
        type = Constants.ACTOR,
        stringResourceId = R.string.search_screen_person,
        getItemName = { name, _ -> name },
        getItemImage = { profilePath, _ -> profilePath ?: ""},
        navigateAction = { itemId, navController ->
            navController.navigate("${ActorScreens.ActorDetailScreen.route}/$itemId")
        }
    )
}