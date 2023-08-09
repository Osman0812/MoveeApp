package com.example.myapplication.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.BottomBarScreen
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreen
import com.example.myapplication.ui.screen.home.movieshome.MoviesHomeScreen
import com.example.myapplication.ui.screen.home.movieshome.MoviesHomeScreenViewModel
import com.example.myapplication.ui.screen.tvseries.TvSeriesScreen
import com.example.myapplication.ui.screen.tvseries.TvSeriesViewModel


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    moviesHomeScreenViewModel: MoviesHomeScreenViewModel,
    tvSeriesViewModel: TvSeriesViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Movies.route
    ) {

        composable(route = BottomBarScreen.Movies.route) {
            MoviesHomeScreen(
                viewModel = moviesHomeScreenViewModel,
                navHostController = navController
            )
        }

        composable(route = "${MoviesScreens.MovieDetailScreen.route}/{movie_id}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movie_id")
            MovieDetailScreen(movieId = movieId!!.toInt())
        }

        composable(route = BottomBarScreen.TvSeries.route) {
             TvSeriesScreen(
                 viewModel = tvSeriesViewModel,
                 navHostController = navController
                 )
        }

    }
}

sealed class MoviesScreens(val route: String) {
    object MovieDetailScreen : MoviesScreens(route = "MOVIE_DETAIL")
}