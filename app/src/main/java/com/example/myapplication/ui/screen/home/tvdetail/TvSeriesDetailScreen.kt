package com.example.myapplication.ui.screen.home.tvdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailModel
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.ui.screen.home.tvseries.TvSeriesRate
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState

@Composable
fun TvDetailScreen(seriesId: Int) {
    val viewModel = hiltViewModel<TvDetailScreenViewModel>()
    val scrollState = rememberScrollState()
    val tvInfo = viewModel.singleTvInfoFlow.collectAsState()
    viewModel.getSingleTvInfo(seriesId)

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
                TvCover(
                    tvInfo = tvInfo
                )
                TvSeriesInfo(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 8.dp),
                    tvInfo = tvInfo
                )
                TvSeriesSummary(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                    tvInfo = tvInfo
                )
            }
        }

        is DataState.Error -> {
            Text("An error occurred! Please try again!")
        }
    }
}

@Composable
fun TvCover(
    modifier: Modifier = Modifier,
    tvInfo: State<DataState<TvSeriesDetailModel>>
) {
    val imagePath = (tvInfo.value as DataState.Success).data.poster_path
    val asyncImage = Constants.IMAGE_URL + imagePath
    Box(modifier = modifier) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            model = asyncImage,
            placeholder = painterResource(id = R.drawable.movies_dummy),
            contentDescription = "null",
            contentScale = ContentScale.Crop
        )
        TvSeriesRate(
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, top = 10.dp)
                .size(60.dp, 25.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.Blue),
            fontSize = 12,
            rate = "8.9"
        )
    }
}

@Composable
private fun CircularProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TvSeriesInfo(
    modifier: Modifier = Modifier,
    tvInfo: State<DataState<TvSeriesDetailModel>>
) {
    val tvSeriesName = (tvInfo.value as DataState.Success).data.name
    val tvSeriesGenresList = (tvInfo.value as DataState.Success).data.genres
    val tvSeriesDuration =
        (tvInfo.value as DataState.Success).data.episode_run_time.getOrNull(0).toString()
    val tvSeriesReleaseDate = (tvInfo.value as DataState.Success).data.first_air_date
    val movieGenre = tvSeriesGenresList.joinToString(separator = ", ") { it.name }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = tvSeriesName,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = movieGenre,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_duration),
                    contentDescription = "Duration Icon"
                )
                if (tvSeriesDuration != "null") {
                    Text(
                        text = "$tvSeriesDuration min",
                        fontSize = 15.sp,
                    )
                }
            }
            Text(
                text = "|",
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendar Icon"
                )
                Text(
                    text = tvSeriesReleaseDate,
                    fontSize = 15.sp,
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
private fun TvSeriesSummary(
    modifier: Modifier = Modifier,
    tvInfo: State<DataState<TvSeriesDetailModel>>,
) {
    val tvOverView = (tvInfo.value as DataState.Success).data.overview
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = tvOverView,
            fontSize = 17.sp,
        )
        when (tvInfo.value) {
            is DataState.Loading -> {
                CircularProgress()
            }

            is DataState.Success -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val labelCreator = stringResource(id = R.string.tv_series_detail_screen_creator)
                    val seasonsList = (tvInfo.value as DataState.Success).data.number_of_seasons
                    val creators = (tvInfo.value as DataState.Success).data.created_by
                    SeasonsView("$seasonsList seasons")
                    val director = creators.joinToString {
                        it.name
                    }
                    TvSummaryExtension(labelName = labelCreator, name = director)
                }
            }

            is DataState.Error -> {
                Text("Data error!")
            }
        }
    }
}

@Composable
private fun TvSummaryExtension(labelName: String, name: String) {
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

@Composable
fun SeasonsView(
    text: String
) {
    Box(
        modifier = Modifier
            .size(width = 81.dp, height = 24.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Gray)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(2.dp),
            color = Color.White
        )
    }
}

@Preview
@Composable
fun TvSeriesDetailScreenPreview() {
    TvDetailScreen(0)
}


