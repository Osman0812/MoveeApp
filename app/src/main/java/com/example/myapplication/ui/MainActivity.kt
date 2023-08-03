package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.screen.login.LoginScreen
import com.example.myapplication.ui.screen.splash.SplashScreen
import com.example.myapplication.ui.screen.webview.WebViewScreen
import com.example.myapplication.ui.screen.login.LoginViewModel
import com.example.myapplication.ui.screen.movieshome.MoviesHomeScreen
import com.example.myapplication.ui.screen.movieshome.MoviesHomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel = viewModel<LoginViewModel>()
            val moviesHomeScreenViewModel : MoviesHomeScreenViewModel by viewModels()
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MoviesHomeScreen.route
                ) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(navHostController = navController)
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(authViewModel, navHostController = navController)
                    }
                    composable("${Screen.WebViewScreen.route}/{url}") { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url") ?: ""
                        WebViewScreen(url = url)
                    }
                    composable(Screen.MoviesHomeScreen.route) {
                        MoviesHomeScreen(moviesHomeScreenViewModel)
                    }
                }
            }
        }
    }
}








