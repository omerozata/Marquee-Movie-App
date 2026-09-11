package com.omero.cleanmovieapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.omero.cleanmovieapp.domain.model.MovieDetail
import com.omero.cleanmovieapp.presentation.components.asMessage
import com.omero.cleanmovieapp.presentation.composable.ErrorState
import com.omero.cleanmovieapp.presentation.composable.MoviePoster

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    DetailContent(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack,
        modifier = modifier
    )
}

@Composable
private fun DetailContent(
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier,
        topBar = { DetailTopBar(onBack = onBack) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    ErrorState(
                        message = state.error.asMessage(),
                        onRetry = {onEvent(DetailEvent.Retry)},
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.movie != null -> {
                    MovieDetailBody(movie = state.movie)
                }
            }
        }
    }
   }

@Composable
private fun MovieDetailBody(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

       MoviePoster(
           posterUrl = movie.poster,
           title = movie.title,
           fallbackTextStyle = MaterialTheme.typography.displayLarge,
           cornerRadius = 16.dp,
           modifier = Modifier
               .fillMaxWidth(0.6f)
               .aspectRatio(2f / 3f)
       )

        Spacer(Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        MovieMetaRow(movie)

      movie.plot?.let {plot ->
          Spacer(Modifier.height(24.dp))
          Text(
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
              text = plot,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = TextAlign.Center
          )
      }

        Spacer(Modifier.height(24.dp))
        HorizontalDivider()

        DetailRow("Genre", movie.genre)
        DetailRow("Director", movie.director)
        DetailRow("Writer", movie.writer)
        DetailRow("Actors", movie.actors)
        DetailRow("Released", movie.released)
        DetailRow("Awards", movie.awards)
        DetailRow("Box Office", movie.boxOffice)


    }
}

@Composable
fun DetailRow(label: String, value: String?) {
    if (value == null) return

    Spacer(Modifier.height(16.dp))

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(2.dp))

        Text(
            modifier = Modifier.padding(8.dp),
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )

    }
}

@Composable
fun MovieMetaRow(movie: MovieDetail) {
    Row(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        movie.year?.let { MetaChip("Year: $it") }
        movie.runtime?.let { MetaChip("Runtime: $it") }
        movie.imdbRating?.let { MetaChip("Rating: $it") }
    }
}

@Composable
fun MetaChip(text: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    onBack: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}