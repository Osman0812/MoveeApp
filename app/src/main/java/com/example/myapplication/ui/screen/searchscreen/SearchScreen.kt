package com.example.myapplication.ui.screen.searchscreen

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.ui.screen.searchscreen.searchscreenuimodel.MediaTypeUiModel
import com.example.myapplication.ui.screen.searchscreen.searchscreenuimodel.SearchUiModel
import com.example.myapplication.util.Constants
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchScreen(
    navController: NavController,
) {

    ColorfulSystemUI(color = Color.Blue)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        SearchBar(
            modifier = Modifier
                .background(color = colorResource(id = R.color.blue))
                .padding(32.dp)
                .fillMaxWidth()
                .height((screenHeight() * 0.20f).dp)
        )
        SearchList(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(24.dp),
            navController = navController
        )
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.search_screen_title),
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            SearchField()
            Text(
                modifier = Modifier
                    .clickable {},
                text = stringResource(id = R.string.search_screen_cancel),
                color = Color.White,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 17.sp),
            )
        }
    }
}

@Composable
private fun SearchField(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .shadow(10.dp)
            .size(
                (screenWidth() * 0.602f).dp,
                (screenHeight() * 0.0562f).dp
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(start = 10.dp),
                painter = painterResource(id = R.drawable.icon_mini_search),
                contentDescription = "Search Icon"
            )
            BasicTextField(
                modifier = Modifier
                    .padding(end = 35.dp),
                value = viewModel.searchState.value,
                onValueChange = { newValue -> viewModel.searchState.value = newValue },
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp),
            )
        }

        if (viewModel.searchState.value.isNotEmpty()) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                onClick = {
                    viewModel.searchState.value = ""
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_mini_x),
                    contentDescription = "X Icon",
                )
            }
        }
    }
}

@Composable
private fun SearchList(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val searchResults = viewModel.searchResultFlow.collectAsLazyPagingItems()
    val searchState = viewModel.searchState

    LaunchedEffect(viewModel.searchState.value) {
        viewModel.handleSearchStateChange(searchState.value)
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        when (searchResults.loadState.refresh) {
            is LoadState.Loading -> {
                if (searchState.value.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            is LoadState.NotLoading -> {
                if (searchState.value.isNotEmpty() && searchResults.itemCount == 0) {
                    item {
                        NotFound(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                items(searchResults.itemCount) { searchResult ->
                    val searchItem = searchResults[searchResult]
                    SingleSearchItem(
                        modifier = Modifier
                            .width((screenWidth() * 0.869f).dp)
                            .height((screenHeight() * 0.156f).dp),
                        navController = navController,
                        searchResult = searchItem!!
                    )
                }
            }

            is LoadState.Error -> {
                item {
                    NotFound(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun ColorfulSystemUI(color: Color) {
    SystemUI(color = color, fileSystemWindows = true)
}

@Composable
private fun SystemUI(color: Color, fileSystemWindows: Boolean) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    activity?.window?.let { WindowCompat.setDecorFitsSystemWindows(it, fileSystemWindows) }
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(color)
    }
}


@Composable
fun screenHeight(): Int {
    return phoneConfiguration().screenHeightDp
}

@Composable
fun screenWidth(): Int {
    return phoneConfiguration().screenWidthDp
}


@Composable
private fun phoneConfiguration(): Configuration {
    return LocalConfiguration.current
}

@Composable
private fun SingleSearchItem(
    modifier: Modifier,
    navController: NavController,
    searchResult: SearchUiModel
) {
    val itemType = getMediaType(searchResult.itemType)
    Surface(
        modifier = modifier
            .clickable {
                itemType.navigate(searchResult.itemId, navController)
            },
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val image = Constants.IMAGE_URL + searchResult.itemImage
            SingleItemImage(
                modifier = Modifier
                    .width((screenWidth() * 0.194f).dp)
                    .fillMaxHeight(),
                imagePath = image
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = searchResult.itemName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = searchResult.itemDescription,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 15.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = itemType.iconResId),
                        contentDescription = "Search Result Icon",
                    )
                    Text(
                        text = stringResource(id = itemType.stringResourceId),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun NotFound(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_result_not_found),
            contentDescription = "Not Found Icon"
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.search_screen_result_not_found),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
            color = colorResource(id = R.color.blue)
        )

    }
}

@Composable
fun SingleItemImage(modifier: Modifier = Modifier, imagePath: String) {
    val imageUrl = Constants.IMAGE_URL + imagePath
    AsyncImage(
        modifier = modifier,
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.movies_dummy),
        contentDescription = "network image",
        error = painterResource(id = R.drawable.movies_dummy),
        contentScale = ContentScale.Crop
    )
}

fun getMediaType(type: String): MediaTypeUiModel {
    return when (type) {
        Constants.MOVIE -> MediaTypeUiModel.Movie
        Constants.TV -> MediaTypeUiModel.TvSeries
        else -> throw IllegalArgumentException("Unknown media type: $type")
    }
}