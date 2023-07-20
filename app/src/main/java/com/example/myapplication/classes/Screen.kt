package com.example.myapplication.classes

sealed class Screen(val route: String) {
    object SplashScreen : Screen(route = "splash_screen")
}