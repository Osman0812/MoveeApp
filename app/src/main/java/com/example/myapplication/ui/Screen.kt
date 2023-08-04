package com.example.myapplication.ui

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
    object WebViewScreen : Screen(route = "web_view_screen")

    object MoviesHomeScreen : Screen(route = "moviesHome")
    object MovieDetailScreen : Screen(route = "movieDetail")

}