package com.example.myapplication.ui.screen.home.actordetail


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.graphs.MoviesScreens
import com.example.myapplication.graphs.TvSeriesDetailScreens
import com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel.ActorCreditUIModel
import com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel.ActorDetailsUi
import com.example.myapplication.ui.screen.home.moviedetail.CircularProgress
import com.example.myapplication.ui.screen.home.moviedetail.MovieDetailScreenViewModel
import com.example.myapplication.ui.screen.home.moviedetail.StatusBarColor
import com.example.myapplication.ui.screen.home.tvseries.TvSeriesRate
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorDetailScreen(actorId: Int, navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val viewModel = hiltViewModel<ActorDetailScreenViewModel>()
    val actorInfo = viewModel.singleActorInfoFlow.collectAsState().value
    val movieCredits = viewModel.movieCreditsFlow.collectAsState().value
    val tvSeriesCredits = viewModel.tvCreditsFlow.collectAsState().value
    viewModel.getSingleActorInfo(actorId)
    viewModel.getMovieCredits(actorId)
    viewModel.getTvSeriesCredits(actorId)
    StatusBarColor()
    when (actorInfo) {
        is DataState.Loading -> {
            CircularProgress()
        }

        is DataState.Success -> {
            Scaffold {
                LazyColumn(

                    modifier = Modifier
                        .padding(bottom = it.calculateBottomPadding().value.dp + 10.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        ActorCover(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((screenHeight / 2.20).dp),
                            actorInfo = actorInfo.data
                        )
                        ActorInfo(actorInfo = actorInfo.data)
                    }

                    when (movieCredits) {
                        is DataState.Success -> {
                            val credits = movieCredits.data.cast
                            items(credits.size) { index ->
                                SingleActorCreditItem(
                                    modifier = Modifier
                                        .padding(start = 32.dp, end = 32.dp),
                                    screenWidth = screenWidth,
                                    credit = ActorCreditUIModel(
                                        id = credits[index].id,
                                        name = credits[index].originalTitle,
                                        imagePath = credits[index].posterPath,
                                        voteAverage = credits[index].voteAverage,
                                        releaseDate = credits[index].releaseDate
                                    ),
                                    navController = navController,
                                    isMovie = true
                                )
                            }
                        }

                        else -> {
                            DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
                        }
                    }
                    when (tvSeriesCredits) {
                        is DataState.Success -> {
                            val credits = tvSeriesCredits.data.crew
                            items(credits.size) { index ->
                                SingleActorCreditItem(
                                    modifier = Modifier
                                        .padding(start = 32.dp, end = 32.dp),
                                    screenWidth = screenWidth,
                                    credit = ActorCreditUIModel(
                                        id = credits[index].id,
                                        name = credits[index].name,
                                        imagePath = credits[index].posterPath,
                                        voteAverage = credits[index].voteAverage,
                                        releaseDate = credits[index].firstAirDate
                                    ),
                                    navController = navController,
                                    isMovie = false
                                )
                            }
                        }

                        else -> {
                            DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
                        }
                    }
                }
            }
        }

        is DataState.Error -> {
            DataState.Error(Exception(MovieDetailScreenViewModel.ErrorMessages.GENERIC_ERROR))
        }
    }
}

@Composable
fun ActorCover(
    modifier: Modifier = Modifier,
    actorInfo: ActorDetailsUi
) {
    val imagePath = actorInfo.profilePath
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
    }
}

@Composable
private fun ActorInfo(
    modifier: Modifier = Modifier,
    actorInfo: ActorDetailsUi
) {
    val actorName = actorInfo.name
    val actorBiography = actorInfo.biography
    val actorBirth = actorInfo.birthday
    val actorBirthPlace = actorInfo.placeOfBirth
    var expanded by remember { mutableStateOf(false) }
    val formatter = DateTimeFormatter.ofPattern("d MMMM", java.util.Locale.getDefault())
    val birthdateFormatted = try {
        val birthdateDate = LocalDate.parse(actorBirth)
        birthdateDate.format(formatter)
    } catch (e: DateTimeParseException) {
        actorBirth
    }

    Column(
        modifier = modifier
            .padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (actorName != null) {
            Text(
                text = actorName,
                style = MaterialTheme.typography.displayLarge
            )
        }
        val maxLines = if (expanded) Int.MAX_VALUE else 4
        if (actorBiography != null) {
            Text(
                text = actorBiography,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
            )
        }
        if (actorBiography != null) {
            if (!expanded && actorBiography.lineSequence().count() > maxLines) {
                Text(
                    text = stringResource(id = R.string.see_full_bio),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                    modifier = Modifier.clickable { expanded = true }
                )
            } else if (expanded) {
                Text(
                    text = stringResource(id = R.string.see_less_bio),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                    modifier = Modifier.clickable { expanded = false }
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = stringResource(id = R.string.actor_birth),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "$birthdateFormatted, $actorBirthPlace"
            )
        }
    }
}

@Composable
private fun SingleActorCreditItem(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    screenWidth: Int,
    credit: ActorCreditUIModel,
    isMovie: Boolean
) {
    Surface(
        modifier = modifier
            .clickable {
                if (isMovie) {
                    navController.navigate("${MoviesScreens.MovieDetailScreen.route}/${credit.id}")
                } else {
                    navController.navigate("${TvSeriesDetailScreens.TvSeriesDetailScreen.route}/${credit.id}")
                }
            },
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val imagePath = "${Constants.IMAGE_URL}/${credit.imagePath}"
            AsyncImage(
                modifier = Modifier
                    .width(
                        width = (screenWidth * 0.194f).dp,
                    )
                    .fillMaxHeight(),
                model = imagePath,
                placeholder = painterResource(id = R.drawable.movies_dummy),
                error = painterResource(id = R.drawable.movies_dummy),
                contentDescription = null
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = credit.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Calendar Icon",

                        )
                    Text(
                        text = credit.releaseDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    Text(
                        text = "|"
                    )

                    TvSeriesRate(
                        modifier = Modifier
                            .size(55.dp, 25.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color.Blue),
                        fontSize = 10,
                        rate = credit.voteAverage.toString()
                    )
                }
            }
        }
    }
}
