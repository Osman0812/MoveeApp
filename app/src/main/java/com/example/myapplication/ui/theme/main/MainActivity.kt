package com.example.myapplication.ui.theme.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.screen.LoginScreen
import com.example.myapplication.ui.theme.screen.MainScreen
import com.example.myapplication.ui.theme.screen.SplashScreen
import com.example.myapplication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = viewModel<AuthViewModel>()
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(navHostController = navController)
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(viewModel, navHostController = navController)
                    }
                    composable(Screen.MainScreen.route){
                        MainScreen()
                    }
                }
            }
        }
    }
}








