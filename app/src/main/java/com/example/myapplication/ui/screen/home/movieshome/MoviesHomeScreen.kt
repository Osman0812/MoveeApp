package com.example.myapplication.ui.screen.home.movieshome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.genresmodel.Genre
import com.example.myapplication.graphs.MoviesScreens
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState

@Composable
fun MoviesHomeScreen(
    viewModel: MoviesHomeScreenViewModel = hiltViewModel(),
    navHostController: NavHostController) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val genres = viewModel.allGenresFlow.collectAsState()

    Background(
        if (scrollState.value <= scrollState.maxValue / 2) {
            Modifier.background(color = Color(0xE6003DFF))
        } else {
            Modifier.background(color = Color.White)
        }
    )
    Column(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.movies_home_screen_movies_title),
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            color = Color.White
        )
        PlayingMoviesList(
            viewModel = viewModel,
            genres = genres,
            navHostController = navHostController
        )


        PopularMovies(
            modifier = Modifier
                .height((screenHeight / 2).dp),
            navHostController = navHostController,
            viewModel = viewModel,
            genres = genres
        )
    }
}

@Composable
private fun Background(modifier: Modifier = Modifier) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2.5f)
                .background(color = Color.White)
        )

    }
}

@Composable
private fun PlayingMoviesList(
    modifier: Modifier = Modifier,
    viewModel: MoviesHomeScreenViewModel,
    genres: State<DataState<List<Genre>>>,
    navHostController: NavHostController
) {
    val lazyRowState = rememberLazyListState()
    val scrollIndex = remember { derivedStateOf { lazyRowState.firstVisibleItemIndex } }
    val movies = viewModel.moviesPlayNowFlow.collectAsLazyPagingItems()
    val currentTitle = remember { mutableStateOf("") }
    val currentAverage = remember { mutableStateOf("") }
    val currentGenres = remember { mutableStateOf("") }
    val currentFirstItem = lazyRowState.firstVisibleItemIndex
    val configuration = LocalConfiguration.current
    val screenWith = configuration.screenWidthDp


    if (movies.itemCount > 0) {
        currentTitle.value = movies.itemSnapshotList.items[scrollIndex.value].title
        currentAverage.value = movies.itemSnapshotList.items[scrollIndex.value].voteAverage.toString()

        LaunchedEffect(currentFirstItem) {
            currentGenres.value = ""
            when (genres.value) {
                is DataState.Loading -> {}
                is DataState.Success -> {
                    val genre = (genres.value as DataState.Success<List<Genre>>).data
                    currentGenres.value = getMovieGenre(
                        genre,
                        movies.itemSnapshotList.items[scrollIndex.value].genreIds
                    )
                }

                is DataState.Error -> {}
            }
        }
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        state = lazyRowState
    ) {
        items(movies.itemCount) { movie ->
            SingleMovieImage(
                modifier = Modifier
                    .width((screenWith * 0.7).dp)
                    .height(screenWith.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .clickable {
                        val movieId = movies[movie]!!.id
                        navHostController.navigate("${MoviesScreens.MovieDetailScreen.route}/$movieId")
                    },
                imagePath = movies[movie]!!.posterPath
            )
        }
    }

    SingleMovieInfo(
        movieName = currentTitle.value,
        voteAverage = currentAverage.value,
        genres = currentGenres.value
    )
}


@Composable
private fun SingleMovieImage(modifier: Modifier = Modifier, imagePath: String) {
    val imageUrl = Constants.IMAGE_URL + imagePath
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.movies_dummy),
        contentDescription = "network image",
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun SingleMovieInfo(
    modifier: Modifier = Modifier,
    movieName: String,
    voteAverage: String,
    genres: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MovieRate(
            modifier = Modifier
                .size(60.dp, 25.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.Blue),
            fontSize = 12,
            rate = voteAverage
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = movieName,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = genres,
            fontSize = 15.sp

        )
        IndicatorLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
private fun PopularMovies(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: MoviesHomeScreenViewModel,
    genres: State<DataState<List<Genre>>>
) {
    val popularMovies = viewModel.moviesPopularFlow.collectAsLazyPagingItems()
    val movieGenre = remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.movies_home_screen_movies_popular),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            items(popularMovies.itemCount) { movie ->
                when (genres.value) {
                    is DataState.Loading -> {}
                    is DataState.Success -> {
                        val genre = (genres.value as DataState.Success<List<Genre>>).data
                        movieGenre.value = getMovieGenre(
                            genre,
                            popularMovies.itemSnapshotList[movie]?.genreIds ?: emptyList()
                        )
                    }

                    is DataState.Error -> {}
                }

                SinglePopularMovie(
                    modifier = Modifier,
                    navHostController=navHostController,
                    imagePath = popularMovies[movie]!!.posterPath,
                    movieName = popularMovies[movie]!!.title,
                    genre = movieGenre.value,
                    voteAverage = popularMovies[movie]!!.voteAverage.toString(),
                    date = popularMovies[movie]!!.releaseDate,
                    movieId = popularMovies[movie]!!.id.toString()
                )
            }
        }
    }
}


@Composable
private fun SinglePopularMovie(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    imagePath: String,
    movieName: String,
    genre: String,
    date: String,
    voteAverage: String,
    movieId: String
) {
    Surface(
        modifier = modifier
            .clickable {
                navHostController.navigate("${MoviesScreens.MovieDetailScreen.route}/$movieId")
            },
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SingleMovieImage(
                modifier = Modifier
                    .size(width = 70.dp, height = 100.dp),
                imagePath = imagePath
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = movieName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = genre,
                    fontSize = 15.sp,
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
                        text = date,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    Text(
                        text = "|"
                    )

                    MovieRate(
                        modifier = Modifier
                            .size(55.dp, 22.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color.Blue),
                        fontSize = 10,
                        rate = voteAverage
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieRate(modifier: Modifier = Modifier, fontSize: Int, rate: String) {
    Row(
        modifier = modifier,
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
            fontSize = fontSize.sp
        )
    }
}
private fun getMovieGenre(movieGenreList: List<Genre>, allGenreList: List<Int>): String {
    val matchingGenres = movieGenreList.filter { it.id in allGenreList }
    return matchingGenres.joinToString(separator = ", ") { it.name }
}

@Preview
@Composable
fun MoviesHomeScreenPreview() {
    MoviesHomeScreen(viewModel(), rememberNavController())
}