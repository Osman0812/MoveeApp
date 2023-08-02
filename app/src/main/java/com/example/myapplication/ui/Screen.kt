package com.example.myapplication.ui

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
    object MainScreen : Screen(route = "main_screen")
    object WebViewScreen : Screen(route = "web_view_screen")
}