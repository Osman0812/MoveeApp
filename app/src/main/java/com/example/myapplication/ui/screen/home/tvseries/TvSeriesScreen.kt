package com.example.myapplication.ui.screen.home.tvseries

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.genresmodel.Genre
import com.example.myapplication.graphs.TvsScreens
import com.example.myapplication.ui.components.IndicatorLine
import com.example.myapplication.ui.screen.home.movieshome.Background
import com.example.myapplication.util.state.DataState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvSeriesScreen(
    viewModel: TvSeriesViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val scrollState = rememberScrollState()
    val genres = viewModel.tvSeriesGenresFlow.collectAsState()
    Background(
        if (scrollState.value <= scrollState.maxValue / 2) {
            Modifier.background(color = Color.Blue)
        } else {
            Modifier.background(color = Color.White)
        }
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.tv_series_title),
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                color = Color.White,
                modifier = Modifier.padding(1.dp)
            )
        }
        item {
            PopularTvSeriesAdded(
                viewModel = viewModel,
                genres = genres,
                navHostController = navHostController
            )
        }
        stickyHeader {
            TopRatedTvSeries(
                viewModel = viewModel,
                navHostController = navHostController
            )
        }
    }
}

@Composable
private fun PopularTvSeriesAdded(
    viewModel: TvSeriesViewModel,
    genres: State<DataState<List<Genre>>>,
    navHostController: NavHostController
) {
    val popularTVSeries = viewModel.tvSeriesPopularFlow.collectAsLazyPagingItems()
    val lazyRowState = rememberLazyListState()
    val scrollIndex = remember { derivedStateOf { lazyRowState.firstVisibleItemIndex } }
    val configuration = LocalConfiguration.current
    val screenWith = configuration.screenWidthDp
    val currentFirstItem = lazyRowState.firstVisibleItemIndex
    val currentTitle = remember { mutableStateOf("") }
    val currentOverview = remember { mutableStateOf("") }
    val currentAverage = remember { mutableStateOf("") }
    val currentGenres = remember { mutableStateOf("") }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val itemWidth = with(LocalDensity.current) { screenWidth * 0.7f }
    val itemHeight = with(LocalDensity.current) { screenHeight * 0.8f }
    if (popularTVSeries.itemCount > 0) {
        currentTitle.value = popularTVSeries.itemSnapshotList.items[scrollIndex.value].name
        currentOverview.value = popularTVSeries.itemSnapshotList.items[scrollIndex.value].overview
        currentAverage.value =
            popularTVSeries.itemSnapshotList.items[scrollIndex.value].vote_average.toString()
        LaunchedEffect(currentFirstItem) {
            currentGenres.value = ""
            when (genres.value) {
                is DataState.Loading -> {}
                is DataState.Success -> {
                    val genre = (genres.value as DataState.Success<List<Genre>>).data
                    currentGenres.value = getTvSeriesGenre(
                        genre,
                        popularTVSeries.itemSnapshotList.items[scrollIndex.value].genre_ids
                    )
                    println(currentGenres.value)
                }

                is DataState.Error -> {
                    println("Genres Failed!")
                }
            }
        }
    }
    LazyRow(
        contentPadding = PaddingValues(start = 32.dp, end = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(screenWith.dp),
        horizontalArrangement = Arrangement.Center,
        state = lazyRowState
    ) {
        items(popularTVSeries.itemCount) { index ->
            val series = popularTVSeries[index]
            if (series != null) {
                SinglePopularTVSeries(
                    modifier = Modifier
                        .width(itemWidth)
                        .height(itemHeight)
                        .clickable {
                            val seriesId = popularTVSeries[index]!!.id
                            navHostController.navigate("${TvsScreens.TvSeriesDetailScreen.route}/$seriesId")
                        },
                    imagePath = series.poster_path,
                )
            }
        }
    }
    TvSeriesInfo(
        movieName = currentTitle.value,
        voteAverage = currentAverage.value,
        genres = currentGenres.value
    )
}

@Composable
private fun SinglePopularTVSeries(
    modifier: Modifier = Modifier,
    imagePath: String,
    tvName: String? = null,
    tvRate: String? = null,
    imageAspectRatio: Float? = null
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val height = screenHeight / 2
    Card(
        elevation = 22.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(15.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { },

        ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            if (imageAspectRatio != null) {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val imageHeight = with(LocalDensity.current) { screenWidth / imageAspectRatio }
                SingleTvImage(
                    modifier = Modifier
                        .height(imageHeight)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    imagePath = imagePath,
                )
            } else {
                SingleTvImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    imagePath = imagePath,
                )
            }
            if (tvName != null) {
                Text(
                    modifier = Modifier.padding(start = 7.dp),
                    text = tvName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            if (tvRate != null) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    TvSeriesRate(
                        modifier = modifier
                            .padding(start = 8.dp)
                            .size(50.dp, 25.dp)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(color = Color.Blue),
                        fontSize = 12,
                        rate = tvRate
                    )
                }
            }
        }
    }
}

@Composable
private fun TopRatedTvSeries(
    viewModel: TvSeriesViewModel,
    navHostController: NavHostController
) {
    val topRatedTvSeries = viewModel.tvSeriesTopRatedFlow.collectAsLazyPagingItems()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.tv_series_top_rated),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        val config = LocalConfiguration.current
        val screenHeight = config.screenHeightDp
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(bottom = 10.dp)
                .height(screenHeight.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 10.dp),
        ) {
            items(topRatedTvSeries.itemCount) { index ->
                val series = topRatedTvSeries[index]
                if (series != null) {
                    SinglePopularTVSeries(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                val seriesId = topRatedTvSeries[index]!!.id
                                navHostController.navigate("${TvsScreens.TvSeriesDetailScreen.route}/$seriesId")
                            },
                        imagePath = series.poster_path,
                        tvName = series.name,
                        tvRate = series.vote_average.toString(),
                        imageAspectRatio = 16 / 9f
                    )
                }
            }
        }
    }
}

private fun getTvSeriesGenre(tvGenreList: List<Genre>, allGenreList: List<Int>): String {
    val matchingGenres = tvGenreList.filter { it.id in allGenreList }
    return matchingGenres.joinToString(separator = ", ") { it.name }
}

@Composable
private fun SingleTvImage(modifier: Modifier = Modifier, imagePath: String) {
    val imageUrl = "https://image.tmdb.org/t/p/original/$imagePath"
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.movies_dummy),
        contentDescription = "network image",
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun TvSeriesInfo(
    modifier: Modifier = Modifier,
    movieName: String,
    voteAverage: String,
    genres: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TvSeriesRate(
            modifier = modifier
                .padding(top = 10.dp)
                .size(50.dp, 25.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color.Blue),
            fontSize = 12,
            rate = voteAverage
        )
        Text(
            text = movieName,
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = genres,
            style = TextStyle(
                fontSize = 15.sp
            )
        )
        IndicatorLine(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
fun TvSeriesRate(modifier: Modifier = Modifier, fontSize: Int, rate: String) {
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
            text = rate,
            style = MaterialTheme.typography.labelSmall,
            fontSize = fontSize.sp
        )
    }
}