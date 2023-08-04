package com.example.myapplication.ui.screen.moviedetail

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.moviecreditsmodel.MovieCreditsModel
import com.example.myapplication.data.model.singlemoviemodel.SingleMovieModel
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MovieDetailScreen(movieId: Int) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val viewModel = hiltViewModel<MovieDetailScreenViewModel>()
    var scrollState = rememberScrollState()
    val movieInfo = viewModel.singleMovieInfoFlow.collectAsState()
    val movieCredits = viewModel.movieCreditsFlow.collectAsState()
    viewModel.getSingleMovieInfo(movieId)
    viewModel.getMovieCredit(movieId)

    StatusBarColor()
    when (movieInfo.value) {
        is DataState.Loading -> {
            CircularProgress()
        }

        is DataState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(bottom=10.dp)
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

    }
}

@Composable
private fun MovieCover(
    modifier: Modifier = Modifier,
    movieInfo: State<DataState<SingleMovieModel>>
) {
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
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movieInfo: State<DataState<SingleMovieModel>>
) {
    val movieName = (movieInfo.value as DataState.Success).data.title
    val movieGenresList = (movieInfo.value as DataState.Success).data.genres
    val movieDuration = (movieInfo.value as DataState.Success).data.runtime.toString()
    val movieReleaseDate = (movieInfo.value as DataState.Success).data.releaseDate

    val movieGenre = movieGenresList.joinToString(separator = ", ") { it.name }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(
            text = movieName,
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
                    text = "$movieDuration min",
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
                    text = movieReleaseDate,
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
private fun MovieSummary(
    modifier: Modifier = Modifier,
    movieInfo: State<DataState<SingleMovieModel>>,
    movieCredits: State<DataState<MovieCreditsModel>>
) {
    val movieOverView = (movieInfo.value as DataState.Success).data.overview
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = movieOverView,
            fontSize = 17.sp,
        )
        when (movieCredits.value) {
            is DataState.Loading -> {
                CircularProgress()
            }

            is DataState.Success -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val directorTag = stringResource(id = R.string.movies_detail_screen_director)
                    val writerTag = stringResource(id = R.string.movies_detail_screen_writers)
                    val starTag = stringResource(id = R.string.movies_detail_screen_stars)

                    val castList = (movieCredits.value as DataState.Success).data.cast
                    val crewList = (movieCredits.value as DataState.Success).data.crew

                    val director = crewList
                        .filter { it.job == "Director" }
                        .joinToString { it.name }


                    val authors = crewList
                        .filter { it.job == "Author" || it.job == "Writer"}
                        .joinToString{ it.name }

                    MovieSummaryExtension(tagName = directorTag, name = director)
                    MovieSummaryExtension(tagName = writerTag, name = authors)
                    MovieSummaryExtension(tagName = starTag, name = "Any Star")
                }
            }

            is DataState.Error -> {
                Text("Data error!")
            }
        }
    }
}

@Composable
private fun MovieSummaryExtension(modifier: Modifier = Modifier, tagName: String, name: String) {
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
private fun StatusBarColor() {
    val systemUIController = rememberSystemUiController()
    LaunchedEffect(key1 = true) {
        systemUIController.isStatusBarVisible = false

    }
}

@Preview
@Composable
fun MoviesDetailScreenPreview() {
    MovieDetailScreen(0)
}