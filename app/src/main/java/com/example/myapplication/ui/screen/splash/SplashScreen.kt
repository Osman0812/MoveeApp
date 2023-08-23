package com.example.myapplication.ui.screen.splash


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.graphs.AuthScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
    SplashScreenBackground()
    SplashScreenLogo()
    SplashScreenBottomView()
    NavigationToLoginScreen(navHostController = navHostController)
}

@Composable
private fun SplashScreenLogo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "content_description_logo",
            modifier = Modifier
                .height(149.dp)
                .width(106.dp)
        )
    }
}

@Composable
private fun SplashScreenBackground() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_background),
            contentDescription = "content_description_splash_screen_background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SplashScreenBottomView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.ic_we),
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.ic_movie),
                color = Color.White
            )
        }
    }
}

@Composable
private fun NavigationToLoginScreen(navHostController: NavHostController) {

    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navHostController.popBackStack()
        navHostController.navigate(AuthScreen.Login.route)
    }
}