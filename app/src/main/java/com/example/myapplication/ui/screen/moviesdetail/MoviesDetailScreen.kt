package com.example.myapplication.ui.screen.moviesdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.IndicatorLine
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MoviesDetailScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    StatusBarColor()
    Column {
        MovieCover(
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 2.20).dp),
        )
        MovieInfo(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp, top = 8.dp)
        )
        MovieSummary(
            modifier = Modifier
                .padding(start=32.dp, end=32.dp, top=16.dp)
        )
    }

}

@Composable
private fun MovieCover(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            painter = painterResource(id = R.drawable.movies_dummy),
            contentDescription = "null",
            contentScale = ContentScale.Crop
        )
        MovieRate(
            modifier = Modifier
                .size(100.dp, 25.dp)
                .align(Alignment.BottomStart)
                .padding(start = 32.dp),
            rate = "8.9"
        )
    }
}

@Composable
private fun MovieInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(
            text = "Movie Name",
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Movie Genre",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black

        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_duration),
                    contentDescription = "Duration Icon"
                )
                Text(
                    text = "122 min",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400
                )
            }

            Text(
                text = "|",
                fontWeight = FontWeight.W400
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendar Icon"
                )
                Text(
                    text = "04.11.2019",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W400
                )
            }
        }
        IndicatorLine(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .height(1.dp)
        )
    }
}

@Composable
private fun MovieSummary(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "movie overview is here",
            fontSize = 17.sp,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val directorTag = stringResource(id = R.string.movies_detail_screen_director)
            val writerTag = stringResource(id = R.string.movies_detail_screen_writers)
            val starTag = stringResource(id = R.string.movies_detail_screen_stars)

            MovieSummaryExtension(tagName = directorTag, name = "Any Director")
            MovieSummaryExtension(tagName = writerTag, name = "Any Writer")
            MovieSummaryExtension(tagName = starTag, name = "Any Star")

        }
    }
}

@Composable
private fun MovieSummaryExtension(modifier: Modifier = Modifier, tagName: String, name:String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = tagName,
            fontSize = 17.sp
        )
        Text(
            text = name,
            fontSize = 17.sp,
            color = Color(0xff003dff)
        )
    }
}

@Composable
private fun MovieRate(modifier: Modifier = Modifier, rate: String) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xff003dff)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            val iconSource = painterResource(id = R.drawable.ic_star)
            Icon(
                painter = iconSource,
                contentDescription = "Star",
                tint = Color.White
            )

            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = rate,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StatusBarColor() {
    val systemUIController = rememberSystemUiController()
    LaunchedEffect(key1 = true) {
        systemUIController.isStatusBarVisible = false

    }
}

@Preview
@Composable
fun MoviesDetailScreenPreview() {
    MoviesDetailScreen()
}