package com.example.myapplication.views


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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.R


@Composable
fun SplashScreen(navHostController: NavHostController){

    Background()
    Logo()
    BottomView()
}
@Composable
fun Logo(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .height(149.dp)
                .width(106.dp)
        )
    }
}


@Composable
fun Background(){
    Box(modifier = Modifier.fillMaxSize(),) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun BottomView(){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.we),
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.kalp),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.Movie),
                color = Color.White
            )
        }
    }
}