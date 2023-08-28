package com.example.myapplication.ui.screen.home.tvdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.graphs.ActorScreens
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreenViewModel
import com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel.TvSeriesUiCredits
import com.example.myapplication.ui.screen.home.tvdetail.tvseriesuimodel.TvSeriesUiModel
import com.example.myapplication.ui.screen.home.tvseries.TvSeriesRate
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState

@Composable
fun TvDetailScreen(seriesId: Int, navHostController: NavHostController) {
    val viewModel = hiltViewModel<TvDetailScreenViewModel>()
    val scrollState = rememberScrollState()
    val tvInfo = viewModel.singleTvInfoFlow.collectAsState().value
    val tvSummary = viewModel.tvCreditsFlow.collectAsState().value
    viewModel.getSingleTvInfo(seriesId)
    viewModel.getTvSeriesCredit(seriesId)

    when (tvInfo) {
        is DataState.Loading -> {
            CircularProgress()
        }

        is DataState.Success -> {
            val tvSeries = tvInfo.data
            when (tvSummary) {
                is DataState.Loading -> {
                    CircularProgress()
                }
                is DataState.Success -> {
                    val summary = tvSummary.data
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                            .padding(bottom = 10.dp)
                    ) {
                        tvSeries.tvPosterPath?.let { image ->
                            tvSeries.tvVoteAverage?.let { vote ->
                                TvCover(
                                    modifier = Modifier,
                                    imagePath = image,
                                    tvRate = vote
                                )
                            }
                        }
                        TvSeriesInfo(
                            modifier = Modifier
                                .padding(start = 32.dp, end = 32.dp, top = 8.dp),
                            tvSeriesUiModel = tvSeries
                        )
                        TvSeriesSummary(
                            modifier = Modifier
                                .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                            tvSeriesUiModel = tvSeries,
                        )
                        Actors(
                            tvCredits = summary,
                            viewModel = viewModel,
                            navHostController = navHostController
                        )
                    }
                }
                is DataState.Error -> {
                    DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
                }
            }
        }

        is DataState.Error -> {
            DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
        }
    }
}

@Composable
fun TvCover(
    modifier: Modifier,
    imagePath: String,
    tvRate: String
) {
    val asyncImage = Constants.IMAGE_URL + imagePath
    Box(modifier = modifier) {
        AsyncImage(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            model = asyncImage,
            placeholder = painterResource(id = R.drawable.movies_dummy),
            contentDescription = "dummy_image",
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
            rate = tvRate
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
    tvSeriesUiModel: TvSeriesUiModel
) {
    val tvSeriesGenre = tvSeriesUiModel.tvGenres?.joinToString(separator = ", ") { it.name }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tvSeriesUiModel.tvTitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.displayLarge
            )
        }
        if (tvSeriesGenre != null) {
            Text(
                text = tvSeriesGenre,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
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
                if (tvSeriesUiModel.tvDuration.toString() != "null") {
                    Text(
                        text = "${tvSeriesUiModel.tvDuration?.firstOrNull()} min",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp)
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
                tvSeriesUiModel.tvReleaseDate?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp)
                    )
                }
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
    modifier: Modifier,
    tvSeriesUiModel: TvSeriesUiModel,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = tvSeriesUiModel.tvOverview.toString(),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val labelCreator = stringResource(id = R.string.tv_series_detail_screen_creator)
            tvSeriesUiModel.createBy?.joinToString { it.name }?.let {
                TvSummaryExtension(
                    labelName = labelCreator,
                    directorName = it
                )
            }
            tvSeriesUiModel.tvNumberOfSeasons?.let {
                SeasonsView("$it seasons")
            }
        }
    }
}

@Composable
private fun TvSummaryExtension(labelName: String, directorName: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = labelName,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp)
        )
        Text(
            text = directorName,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
            color = colorResource(id = R.color.blue)
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
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(2.dp),
            color = Color.White
        )
    }
}

@Composable
fun Actors(
    tvCredits: TvSeriesUiCredits,
    viewModel: TvDetailScreenViewModel,
    navHostController: NavHostController
) {
    val scrollState = rememberScrollState()
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.padding(32.dp)
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
            val castList = tvCredits.cast
            if (!castList.isNullOrEmpty()) {
                for (actor in castList) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .clickable {
                                navHostController.navigate("${ActorScreens.ActorDetailScreen.route}/${actor.id}")
                            },
                        ) {
                        val profileImageUrl =
                            viewModel.createProfileImageUrl(actor.profilePath)
                        ActorCircle(
                            photoUrl = profileImageUrl,
                            actorName = actor.name
                        )
                    }
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
