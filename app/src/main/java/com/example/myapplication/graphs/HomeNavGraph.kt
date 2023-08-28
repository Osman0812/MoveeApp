package com.example.myapplication.graphs


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.BottomBarScreen
import com.example.myapplication.ui.screen.home.actordetail.ActorDetailScreen
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreen
import com.example.myapplication.ui.screen.home.movieshome.MoviesHomeScreen
import com.example.myapplication.ui.screen.home.tvseries.TvSeriesScreen
import com.example.myapplication.ui.screen.home.tvdetail.TvDetailScreen
import com.example.myapplication.ui.screen.searchscreen.SearchScreen


@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Movies.route
    ) {

        composable(route = BottomBarScreen.Movies.route) {
            MoviesHomeScreen(
                navHostController = navController
            )
        }

        composable(route = "${MoviesScreens.MovieDetailScreen.route}/{movie_id}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movie_id")
            if (movieId != null) {
                MovieDetailScreen(movieId = movieId.toInt())
            }
        }

        composable(route = BottomBarScreen.TvSeries.route) {
            TvSeriesScreen(
                viewModel = hiltViewModel(),
                navHostController = navController
            )

        }
        composable(route = "${TvSeriesDetailScreens.TvSeriesDetailScreen.route}/{series_id}") { backStackEntry ->
            val seriesId = backStackEntry.arguments?.getString("series_id")
            if (seriesId != null) {
                TvDetailScreen(seriesId = seriesId.toInt(),navController)
            }
        }

        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(
                navController = navController
            )
        }
        composable(route = "${ActorScreens.ActorDetailScreen.route}/{actor_id}") { backStackEntry ->
            val actorId = backStackEntry.arguments?.getString("actor_id")
            if (actorId != null) {
                ActorDetailScreen(actorId = actorId.toInt(), navController = navController)
            }
        }
    }
}
sealed class MoviesScreens(val route: String) {
    object MovieDetailScreen : MoviesScreens(route = "MOVIE_DETAIL")
}
sealed class TvSeriesDetailScreens(val route: String) {
    object TvSeriesDetailScreen : TvSeriesDetailScreens(route = "TV_SERIES_DETAIL")
}
sealed class ActorScreens(val route: String) {
    object ActorDetailScreen : ActorScreens(route = "ACTOR_DETAIL")
}
