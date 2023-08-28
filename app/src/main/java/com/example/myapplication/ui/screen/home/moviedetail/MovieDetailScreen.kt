package com.example.myapplication.ui.screen.home.moviedetail

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.ui.screen.home.moviedetail.movieuimodel.MovieUiModel
import com.example.myapplication.ui.screen.home.moviedetail.movieuimodel.MoviesCreditsUiModel
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MovieDetailScreen(movieId: Int) {
    val viewModel = hiltViewModel<MovieDetailScreenViewModel>()
    val scrollState = rememberScrollState()

    val movieInfoState = viewModel.singleMovieInfoFlow.collectAsState().value
    val movieCreditsState = viewModel.movieCreditsFlow.collectAsState().value

    LaunchedEffect(key1 = movieId) {
        viewModel.getSingleMovieInfo(movieId)
        viewModel.getMovieCredit(movieId)
    }

    StatusBarColor()

    when (movieInfoState) {
        is DataState.Loading -> CircularProgress()

        is DataState.Success -> {
            val movies = movieInfoState.data
            val credits = when (movieCreditsState) {
                is DataState.Success -> movieCreditsState.data
                else -> MoviesCreditsUiModel()
            }

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(bottom = 10.dp)
            ) {
                movies.posterPath?.let { imagePath ->
                    MovieCover(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((LocalConfiguration.current.screenHeightDp / 2.20).dp),
                        imagePath = imagePath,
                        movieRate = movies.voteAverage.toString()
                    )
                }
                MovieInfo(
                    modifier = Modifier
                        .padding(start = 32.dp, end = 32.dp, top = 8.dp),
                    movieUiModel = movies
                )
                movies.overview?.let { overview ->
                    MovieSummary(
                        modifier = Modifier
                            .padding(start = 32.dp, end = 32.dp, top = 16.dp),
                        overview = overview,
                        moviesCreditsUiModel = credits
                    )
                }
            }
        }

        is DataState.Error -> DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
    }
}


@Composable
fun MovieCover(
    modifier: Modifier = Modifier,
    imagePath: String,
    movieRate: String
) {
    val asyncImage = Constants.IMAGE_URL + imagePath
    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            model = asyncImage,
            placeholder = painterResource(id = R.drawable.movies_dummy),
            contentDescription = "dummy_image",
            contentScale = ContentScale.FillWidth
        )
        MovieRate(
            modifier = Modifier
                .size(100.dp, 25.dp)
                .align(Alignment.BottomStart)
                .padding(start = 32.dp),
            rate = movieRate
        )
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movieUiModel: MovieUiModel
) {
    val movieGenre = movieUiModel.movieGenres?.joinToString(separator = ", ") { it.name }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        movieUiModel.movieTitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.displayLarge
            )
        }
        if (movieGenre != null) {
            Text(
                text = movieGenre,
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
                Text(
                    text = "${movieUiModel.movieDuration} min",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp)
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
                    text = movieUiModel.movieReleaseDate.toString(),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp)
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
    overview: String,
    moviesCreditsUiModel: MoviesCreditsUiModel
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = overview,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val labelDirector = stringResource(id = R.string.movies_detail_screen_director)
            val labelWriter = stringResource(id = R.string.movies_detail_screen_writers)
            val labelStar = stringResource(id = R.string.movies_detail_screen_stars)

            moviesCreditsUiModel.director?.let {
                SummaryExtension(
                    labelName = labelDirector,
                    name = it
                )
            }

            moviesCreditsUiModel.writer?.let {
                SummaryExtension(
                    labelName = labelWriter,
                    name = it
                )
            }

            moviesCreditsUiModel.stars?.let {
                SummaryExtension(
                    labelName = labelStar,
                    name = it
                )
            }
        }
    }
}

@Composable
fun SummaryExtension(labelName: String, name: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = labelName,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
            color = colorResource(id = R.color.blue)
        )
    }
}

@Composable
private fun MovieRate(
    modifier: Modifier = Modifier,
    rate: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.blue)),
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
fun CircularProgress() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun StatusBarColor() {
    val systemUIController = rememberSystemUiController()
    LaunchedEffect(key1 = false) {
        systemUIController.isStatusBarVisible = false
    }
}

@Preview
@Composable
fun MoviesDetailScreenPreview() {
    MovieDetailScreen(0)
}