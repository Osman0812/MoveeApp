package com.example.myapplication.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.myapplication.ui.screen.login.LoginScreen
import com.example.myapplication.ui.screen.login.LoginViewModel
import com.example.myapplication.ui.screen.splash.SplashScreen
import com.example.myapplication.ui.screen.webview.WebViewScreen


fun NavGraphBuilder.authNavGraph(
    navHostController: NavHostController,
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(
                navHostController = navHostController,
            )
        }

        composable(route = AuthScreen.Splash.route) {
            SplashScreen(navHostController = navHostController)
        }

        composable(
            route = "${AuthScreen.WebView.route}/{url}",
            arguments = listOf(navArgument("url") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url)
        }

    }
}

sealed class AuthScreen(val route: String) {
    object Splash : AuthScreen(route = "splash")
    object Login : AuthScreen(route = "login")
    object WebView : AuthScreen(route = "web_view_screen")
}