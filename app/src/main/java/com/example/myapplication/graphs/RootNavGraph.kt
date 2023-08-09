package com.example.myapplication.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.myapplication.ui.screen.home.HomeScreen
import com.example.myapplication.ui.screen.login.LoginScreen
import com.example.myapplication.ui.screen.login.LoginViewModel
import com.example.myapplication.ui.screen.home.movieshome.MoviesHomeScreen
import com.example.myapplication.ui.screen.home.movieshome.MoviesHomeScreenViewModel
import com.example.myapplication.ui.screen.splash.SplashScreen
import com.example.myapplication.ui.screen.tvseries.TvSeriesScreen
import com.example.myapplication.ui.screen.tvseries.TvSeriesViewModel
import com.example.myapplication.ui.screen.webview.WebViewScreen

@Composable
fun RootNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    moviesHomeScreenViewModel: MoviesHomeScreenViewModel,
    tvSeriesViewModel: TvSeriesViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {

        authNavGraph(
            navHostController = navController,
            loginViewModel = loginViewModel
        )

        composable(route = Graph.HOME) {
           HomeScreen(tvSeriesViewModel = tvSeriesViewModel,moviesHomeScreenViewModel = moviesHomeScreenViewModel)
        }
    }

}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAIL = "details_graph"
    const val TV = "tv_graph"
}