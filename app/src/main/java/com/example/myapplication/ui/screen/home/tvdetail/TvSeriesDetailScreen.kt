package com.example.myapplication.ui.screen.home.tvdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.data.model.singletvmodel.TvSeriesDetailModel
import com.example.myapplication.data.model.tvseriescreditsmodel.TvSeriesCreditsModel
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.ui.screen.home.tvseries.TvSeriesRate
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState

@Composable
fun TvDetailScreen(seriesId: Int) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val viewModel = hiltViewModel<TvDetailScreenViewModel>()
    val scrollState = rememberScrollState()
    val tvInfo = viewModel.singleTvInfoFlow.collectAsState()
    val tvCredits = viewModel.tvCreditsFlow.collectAsState()
    viewModel.getSingleTvInfo(seriesId)
    viewModel.getTvSeriesCredit(seriesId)

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenHeight / 2.20).dp),
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
                    tvInfo = tvInfo,
                    tvCredits = tvCredits,
                    viewModel = viewModel
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
                Text(
                    text = "$tvSeriesDuration min",
                    fontSize = 15.sp,
                )
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
    tvCredits: State<DataState<TvSeriesCreditsModel>>,
    viewModel: TvDetailScreenViewModel
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
                    Actors(tvCredits, viewModel)
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
                .size(width = 90.dp, height = 24.dp)
                .padding(start = 3.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            color = Color.White
        )
    }
}

@Composable
fun Actors(
    tvCredits: State<DataState<TvSeriesCreditsModel>>,
    viewModel: TvDetailScreenViewModel
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = R.string.tv_series_cast),
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            when (tvCredits.value) {
                is DataState.Loading -> {
                }
                is DataState.Success -> {
                    val castList = (tvCredits.value as DataState.Success).data.cast
                    for (actor in castList) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {

                            val profileImageUrl =
                                viewModel.createProfileImageUrl(actor.profile_path)
                            ActorCircle(
                                photoUrl = profileImageUrl,
                                actorName = actor.name
                            )
                        }
                    }
                }
                is DataState.Error -> {
                    Text("Data error!")
                }
            }
        }
    }
}

@Composable
fun ActorCircle(
    photoUrl: String,
    actorName: String
) {
    val circleSize = 80.dp
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(circleSize)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = photoUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
    Text(text = actorName)
}

@Preview
@Composable
fun TvSeriesDetailScreenPreview() {
    TvDetailScreen(0)
}


