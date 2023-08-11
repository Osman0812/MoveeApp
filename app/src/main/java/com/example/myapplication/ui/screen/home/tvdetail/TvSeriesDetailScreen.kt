package com.example.myapplication.ui.screen.home.tvdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.singlemoviemodel.SingleMovieModel
import com.example.myapplication.ui.screen.home.tvseries.TvSeriesRate
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState

@Composable
fun TvDetailScreen(seriesId : Int){

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp


/*
    when (tvInfo.value) {
        is DataState.Loading -> {
            CircularProgress()
        }

        is DataState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(bottom = 10.dp)
            ) {
                MovieCover(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenHeight / 2.20).dp),
                    movieInfo = movieInfo
                )
                MovieInfo(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 8.dp),
                    movieInfo = movieInfo
                )
                MovieSummary(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                    movieInfo = movieInfo,
                    movieCredits = movieCredits
                )
            }
        }

        is DataState.Error -> {
            Text("An error occurred! Please try again!")
        }

 */

}
@Composable
fun TvCover(
    modifier: Modifier = Modifier,
    movieInfo: State<DataState<SingleMovieModel>>
){
    val imagePath = (movieInfo.value as DataState.Success).data.posterPath
    val asyncImage = Constants.IMAGE_URL + imagePath
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            model = asyncImage,
            placeholder = painterResource(id = R.drawable.movies_dummy),
            contentDescription = "null",
            contentScale = ContentScale.FillWidth
        )
        TvSeriesRate(
            modifier = Modifier
                .size(100.dp, 25.dp)
                .align(Alignment.BottomStart)
                .padding(start = 32.dp),
            fontSize = 12,
            rate = "8.9"
        )
    }
}

@Composable
private fun TvSummaryExtension(modifier: Modifier = Modifier, labelName: String, name: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = labelName,
            fontSize = 17.sp
        )
        Text(
            text = name,
            fontSize = 17.sp,
            color = Color(0xff003dff)
        )
    }
}