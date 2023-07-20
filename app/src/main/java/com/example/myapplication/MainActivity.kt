package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.classes.Screen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.screen.LoginScreen
import com.example.myapplication.ui.theme.screen.SplashScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
                    composable(Screen.SplashScreen.route) {
                        SplashScreen(navHostController = navController)
                    }
                    composable(Screen.LoginScreen.route) {
                        LoginScreen()
                    }
                }
            }
        }
    }
}








