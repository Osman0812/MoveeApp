package com.example.myapplication.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
    object LoginScreen : Screen(route = "login_screen")
    object MainScreen : Screen(route = "main_screen")
}