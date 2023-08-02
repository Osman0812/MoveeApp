package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.screen.login.LoginScreen
import com.example.myapplication.ui.screen.main.MainScreen
import com.example.myapplication.ui.screen.splash.SplashScreen
import com.example.myapplication.ui.screen.webview.WebViewScreen
import com.example.myapplication.ui.screen.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<LoginViewModel>()
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.SplashScreen.route
                ) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(navHostController = navController)
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(viewModel, navHostController = navController)
                    }
                    composable(Screen.MainScreen.route) {
                        MainScreen()
                    }
                    composable("${Screen.WebViewScreen.route}/{url}") { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url") ?: ""
                        WebViewScreen(url = url)
                    }
                }
            }
        }
    }
}








