package com.example.myapplication.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.graphs.RootNavGraph
import com.example.myapplication.ui.screen.home.movieshome.MoviesHomeScreenViewModel
import com.example.myapplication.ui.screen.login.LoginViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel = viewModel<LoginViewModel>()
            val moviesHomeScreenViewModel: MoviesHomeScreenViewModel by viewModels()
            MyApplicationTheme {
                RootNavGraph(
                    navController = rememberNavController(),
                    loginViewModel = authViewModel,
                    moviesHomeScreenViewModel = moviesHomeScreenViewModel
                )
            }
        }
    }
}








