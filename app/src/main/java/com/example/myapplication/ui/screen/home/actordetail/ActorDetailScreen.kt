package com.example.myapplication.ui.screen.home.actordetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.actormodel.ActorDetailsModel
import com.example.myapplication.ui.screen.home.actordetail.actordetailsuimodel.ActorDetailsUIModel
import com.example.myapplication.ui.screen.home.moviedetail.CircularProgress
import com.example.myapplication.ui.screen.home.moviedetail.StatusBarColor
import com.example.myapplication.util.Constants
import com.example.myapplication.util.state.DataState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActorDetailScreen(actorId: Int) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val viewModel = hiltViewModel<ActorDetailScreenViewModel>()
    val actorInfo = viewModel.singleActorInfoFlow.collectAsState().value
    val scrollState = rememberScrollState()
    viewModel.getSingleActorInfo(actorId)
    StatusBarColor()
    when (actorInfo) {
        is DataState.Loading -> {
            CircularProgress()
        }
        is DataState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(bottom = 10.dp)
                    .fillMaxSize()
            ) {
                ActorCover(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((screenHeight / 2.20).dp),
                    actorInfo = actorInfo.data
                )
                ActorInfo(actorInfo = actorInfo.data)
            }
        }
        is DataState.Error -> {
            Text("An error occurred! Please try again!")
        }
    }
}

@Composable
fun ActorCover(
    modifier: Modifier = Modifier,
    actorInfo: ActorDetailsUIModel
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ActorInfo(
    modifier: Modifier = Modifier,
    actorInfo: ActorDetailsUIModel
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
                    text = "See full bio >>",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                    modifier = Modifier.clickable { expanded = true }
                )
            } else if (expanded) {
                Text(
                    text = "See less >>",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Blue),
                    modifier = Modifier.clickable { expanded = false }
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
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
